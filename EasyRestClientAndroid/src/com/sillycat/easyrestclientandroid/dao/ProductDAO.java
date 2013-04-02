package com.sillycat.easyrestclientandroid.dao;

import java.util.List;

import com.sillycat.easyrestclientandroid.model.Product;

public interface ProductDAO {

	public Product insert(Product product);

	public List<Product> all();

	public Product get(Long id);

	public List<Product> pagination(int currentPage, int pageSize);

	public int size();
	
	public boolean deleteById(Long productId);
	
	public Product update(Product product);

}
