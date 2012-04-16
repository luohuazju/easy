package com.sillycat.easywebflow.service;

import java.util.List;

import com.sillycat.easywebflow.model.Product;

public interface ProductService {

	public Product getProduct(Integer productId);

	public List<Product> getProducts();

}
