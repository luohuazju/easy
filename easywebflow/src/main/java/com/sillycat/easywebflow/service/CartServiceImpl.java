package com.sillycat.easywebflow.service;

import org.springframework.stereotype.Service;

import com.sillycat.easywebflow.model.Cart;
import com.sillycat.easywebflow.model.CartItem;
import com.sillycat.easywebflow.model.Product;

@Service("cartService")
public class CartServiceImpl implements CartService {

	public void addItem(Product product, Cart cart) {
		Integer id = product.getId();
		CartItem item = cart.getMap().get(id);
		if (null != item) {
			// product in cart, add one
			item.setQuantity(item.getQuantity() + 1);
		} else {
			// create a new product in cart
			cart.getMap().put(id, new CartItem(product, 1));
		}
	}

}
