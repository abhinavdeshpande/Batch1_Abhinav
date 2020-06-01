package com.cg;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Product Cart using logger and swagger")
public class ProductCartController {
	
	@Autowired
	private ProductCrudServiceProxy proxy;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductCartController.class);
	
	private List<ProductCart> products = new ArrayList<ProductCart>();
	
	@GetMapping(path = "/product-cart/cart/{name}")
	@ApiOperation(value = "addToCart", nickname = "addToCart")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ProductCart.class),
			@ApiResponse(code = 500, message = "Failure", response = ProductCart.class) })
    public List<ProductCart> addToCart(@PathVariable String name, HttpServletRequest req) {
        System.out.println("inside doSessionManagement and going to add item in the session");
        List<ProductCart> products = null;
        HttpSession session = req.getSession(); //enable the session.
        //HttpSession session = null;
        if(session != null ) {
            Object obj = session.getAttribute("products");
            if(obj != null && obj instanceof List<?>) {
                products = (List<ProductCart>)session.getAttribute("products");
                System.out.println("The items added in the session " + products);
                ProductCart product = proxy.retrieveProductByName(name);
                products.add(product);
                session.setAttribute("products", products);
            }else {
                System.out.println("The session is created for the first time");
                products = new ArrayList<ProductCart>();
                ProductCart product = proxy.retrieveProductByName(name);
                products.add(product);   
                session.setAttribute("products", products);
            }
           
        }else {
            System.out.println("session is null");
            ProductCart product = proxy.retrieveProductByName(name);
            this.products.add(product);
            products = this.products;
        }
        System.out.println("returning the shopping cart: " + products);
        return products;
    }


}
