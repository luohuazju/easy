package com.sillycat.easywebflow.model;

import java.io.Serializable;

public class CartItem implements Serializable {

	private static final long serialVersionUID = 2042190814005605108L;

	private Product product;

	private Integer quantity;

	public CartItem(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
