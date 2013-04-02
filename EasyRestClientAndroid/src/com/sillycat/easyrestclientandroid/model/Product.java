package com.sillycat.easyrestclientandroid.model;

public class Product {
	
	//===========database
	public static final String TABLE_NAME = "product";
	public static final String COLUMN_NAME_PRODUCT_ID = "product_id";
	public static final String COLUMN_NAME_PRODUCT_NAME = "product_name";
	public static final String COLUMN_NAME_PRODUCT_PRICE = "product_price";
	public static final String COLUMN_NAME_PRODUCT_DESN = "product_desn";
	public static final String COLUMN_NAME_PRODUCT_IMAGE_URL = "product_imageurl";
	
	public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
			COLUMN_NAME_PRODUCT_ID + " INTEGER PRIMARY KEY," + 
			COLUMN_NAME_PRODUCT_NAME + " TEXT," + 
			COLUMN_NAME_PRODUCT_PRICE + " TEXT," + 
			COLUMN_NAME_PRODUCT_DESN + " TEXT," + 
			COLUMN_NAME_PRODUCT_IMAGE_URL + " TEXT" + 
			");";
	public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
	//===========database

	private String productName;

	private String productPrice;

	private String productDesn;

	private Long productId;

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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductImageURL() {
		return productImageURL;
	}

	public void setProductImageURL(String productImageURL) {
		this.productImageURL = productImageURL;
	}

}
