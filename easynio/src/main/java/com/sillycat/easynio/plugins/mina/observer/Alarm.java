package com.sillycat.easynio.plugins.mina.observer;

import java.util.Observable;
import java.util.Observer;

public class Alarm implements Observer {

	public void makeAlarm() {
		System.out.println("Alarm, alarm, the water is boiled! ");
	}

	public void update(Observable o, Object arg) {
		makeAlarm();
	}

}
