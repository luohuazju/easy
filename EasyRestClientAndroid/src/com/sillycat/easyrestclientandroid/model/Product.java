package com.sillycat.easyrestclientandroid.model;

public class Product {

	private String productName;

	private String productPrice;

	private String productDesn;

	private Integer productId;

	private String productImageURL;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDesn() {
		return productDesn;
	}

	public void setProductDesn(String productDesn) {
		this.productDesn = productDesn;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductImageURL() {
		return productImageURL;
	}

	public void setProductImageURL(String productImageURL) {
		this.productImageURL = productImageURL;
	}

}
