package com.sillycat.easyhunter.model;

import org.apache.solr.client.solrj.beans.Field;

public class Product {

	@Field
	private String id;

	@Field
	private String name;

	@Field
	private String manu;

	@Field
	private String[] cat;

	@Field
	private String[] features;

	@Field
	private float price;

	@Field
	private int popularity;

	@Field
	private boolean inStock;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManu() {
		return manu;
	}

	public void setManu(String manu) {
		this.manu = manu;
	}

	public String[] getCat() {
		return cat;
	}

	public void setCat(String[] cat) {
		this.cat = cat;
	}

	public String[] getFeatures() {
		return features;
	}

	public void setFeatures(String[] features) {
		this.features = features;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

}
