package com.sillycat.easywebflow.service;

import com.sillycat.easywebflow.model.Cart;
import com.sillycat.easywebflow.model.Product;

public interface CartService {

	public void addItem(Product product, Cart cart);
}
