package com.sillycat.easynio.plugins.mina.observer;

import java.util.Observable;
import java.util.Observer;

public class Sage implements Observer {

	public void update(Observable o, Object arg) {
		SexyGirl subject = (SexyGirl) o;
		if (subject != null) {
			System.out.println("I am sage, sexy is empty to me!");
		}
	}

}
