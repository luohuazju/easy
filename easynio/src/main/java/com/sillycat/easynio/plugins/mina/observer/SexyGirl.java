package com.sillycat.easynio.plugins.mina.observer;

import java.util.Observable;

public class SexyGirl extends Observable {

	private String state = "With All Clothes!";

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void dance() {
		this.state = " Without all Clothes!";
		this.setChanged();
		this.notifyObservers();
	}

}
