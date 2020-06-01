package com.cg;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Product Catalogue using logger and swagger")
public class ProductController {
	
	@Autowired
	private ProductRepository repos;
	
	private static final Logger logger = LoggerFactory.getLogger(Product.class);
	
	@GetMapping(path = "/products-list")
	@ApiOperation(value = "retrieveAllProducts", nickname = "retrieveAllProducts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 500, message = "Failure", response = Product.class) })
	public List<Product> retrieveAllProducts() {
		System.out.println("inside retrieveAllProd() of Controller");
		return repos.findAll();
	}

	@GetMapping(path = "/product/{name}")
	@ApiOperation(value = "retrieveProductByName", nickname = "retrieveProductByName")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 500, message = "Failure", response = Product.class) })
	public Product retrieveProductByName(@PathVariable String name) {
		System.out.println("inside retrieveProduct() of Controller");
		Product product = repos.findByProductName(name);
		System.out.println("returned product " + product);
		return product;
	}

	@PostMapping(path = "/add-product")
	@ApiOperation(value = "addProduct", nickname = "addProduct")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 500, message = "Failure", response = Product.class) })
	public void addProduct(@Valid @RequestBody Product product) {
		System.out.println("inside addProduct() of Controller");
		repos.save(product);
	}

	@DeleteMapping(path = "/delete-product/{id}")
	@ApiOperation(value = "deleteProduct", nickname = "deleteProduct")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 500, message = "Failure", response = Product.class) })
	public void deleteProduct(@PathVariable long id) {
		repos.deleteById(id);
	}

	@PutMapping(path = "/update-product")
	@ApiOperation(value = "updateProduct", nickname = "updateProduct")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 500, message = "Failure", response = Product.class) })
	public void updateProduct(@RequestBody Product product) {
		System.out.println("inside updateProduct() of Controller");
		repos.deleteById(product.getId());
		repos.save(product);
	}
	
	@GetMapping(path = "/list-all-products")
	@HystrixCommand(fallbackMethod = "retrieveAllProducts")
	@ApiOperation(value = "retrieveProducts", nickname = "retrieveProducts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Product.class),
			@ApiResponse(code = 500, message = "Failure", response = Product.class) })
	public List<Product> retrieveProducts() {
		System.out.println("Inside retrieveProducts");
		System.out.println("Calling to fallbackMethod = \"retrieveAllProducts\"");
		throw new RuntimeException("Not Available");
	}
}
