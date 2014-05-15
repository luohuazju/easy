package com.sillycat.easyspringrabbitmqpublisher.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sillycat.easyspringrabbitmqpublisher.model.Product;

@Component
@Scope("prototype")
@Path("/product")
public class ProductJerseyService {

	private static final Logger logger = Logger.getLogger(ProductJerseyService.class);
	
	@GET
	@Path("products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> listProducts(){
		logger.debug("hitting the products REST API");
		List<Product> items = new ArrayList<Product>();
		
		Product item1 = new Product();
		item1.setDesn("desn1");
		item1.setId(Long.valueOf(1));
		item1.setProductLink("http://github.com/luohuazju");
		item1.setProductName("My Github Linke");
		
		Product item2 = new Product();
		item2.setDesn("desn2");
		item2.setId(Long.valueOf(2));
		item2.setProductLink("http://sillycat.iteye.com");
		item2.setProductName("My Technical Blog");
		
		items.add(item1);
		items.add(item2);
		return items;
	}
	
	@GET
	@Path("products/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("id") Long id){
		logger.debug("hitting the get product REST API with id = " + id);
		Product item1 = new Product();
		item1.setDesn("desn1");
		item1.setId(Long.valueOf(1));
		item1.setProductLink("http://github.com/luohuazju");
		item1.setProductName("My Github Linke");
		
		return item1;
	}
	
	@POST
	@Path("products")
	@Produces(MediaType.APPLICATION_JSON)
	public Product saveProduct(Product item){
		logger.debug("hitting the save product REST API");
		logger.debug("id=" + item.getId());
		logger.debug("desn=" + item.getDesn());
		logger.debug("link=" + item.getProductLink());
		logger.debug("name=" + item.getProductName());
		return item;
	}
	
	
}
