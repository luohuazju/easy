package com.sillycat.easynio.plugins.mina.observer;

import java.util.Observable;
import java.util.Observer;

public class Display extends Observable implements Observer {

	private String status = "Not yet!";

	public void setStatus(String status) {
		this.status = status;
	}

	public void displayTemputer(int temperature) {
		System.out.println("Status: " + status + " Temprature: " + temperature
				+ "");
		if (temperature > 95) {
			this.setStatus("Boiled!");
			this.setChanged();
			this.notifyObservers();
		}
	}

	public void update(Observable o, Object arg) {
		System.out.print( "num is i = " + arg + " ");
		displayTemputer(((Heater) o).getTemperature());
	}

}
