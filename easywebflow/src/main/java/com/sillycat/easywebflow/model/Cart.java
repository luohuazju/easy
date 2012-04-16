package com.sillycat.easywebflow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart implements Serializable {

	private static final long serialVersionUID = 180082200907278137L;

	private Map<Integer, CartItem> map = new HashMap<Integer, CartItem>();

	public Map<Integer, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<Integer, CartItem> map) {
		this.map = map;
	}
	
	public boolean isEmpty(){
		if(map == null || map.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public List<CartItem> getItems() {
		return new ArrayList<CartItem>(map.values());
	}
	
	public Integer getTotalPrice() {
		int total = 0;
		for (CartItem item : map.values()) {
			total += item.getProduct().getPrice() * item.getQuantity();
		}
		return total;
	}

}
