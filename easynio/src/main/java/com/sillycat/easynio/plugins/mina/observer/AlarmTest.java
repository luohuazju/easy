package com.sillycat.easynio.plugins.mina.observer;

public class AlarmTest {

	public static void main(String[] args) {
		Heater header = new Heater();
		Display display = new Display();
		Alarm alarm = new Alarm();
		
		header.addObserver(display);
		display.addObserver(alarm);
		header.boilWater();
	}

}
