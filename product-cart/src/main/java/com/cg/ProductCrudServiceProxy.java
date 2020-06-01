package com.cg;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="product-crud-operations", url="http://localhost:8001")
@FeignClient(name="product-crud-operations")
@RibbonClient(name="product-crud-operations")
public interface ProductCrudServiceProxy {
	
	@GetMapping(path = "/product/{name}")
	public ProductCart retrieveProductByName(@PathVariable String name);
	
}
