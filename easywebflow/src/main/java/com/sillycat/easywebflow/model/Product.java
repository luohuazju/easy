package com.sillycat.easywebflow.model;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = -7097784230836499051L;

	private Integer id;

	private String description;

	private Integer price;

	public Product(Integer id, String description, Integer price) {
		this.id = id;
		this.description = description;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
