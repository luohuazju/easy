package com.sillycat.easywebflow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sillycat.easywebflow.model.Product;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private Map<Integer, Product> products = new HashMap<Integer, Product>();

	public ProductServiceImpl() {
		products.put(1, new Product(1, "Dog", 1000));
		products.put(2, new Product(2, "Cat", 2000));
		products.put(3, new Product(3, "fish", 2300));
	}

	public List<Product> getProducts() {
		return new ArrayList<Product>(products.values());
	}

	public Product getProduct(Integer productId) {
		return products.get(productId);
	}
}
