package com.sillycat.easyrestclientandroid.dao;

import java.util.List;

import com.sillycat.easyrestclientandroid.model.Product;

public interface ProductDAO {

	public List<Product> all();
	
	public Product get(Integer id);
	
	public List<Product> pagination(int currentPage, int pageSize);
	
	public int size();
	
}
