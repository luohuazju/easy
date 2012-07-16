package com.sillycat.easynio.plugins.mina.observer;

import java.util.Observable;
import java.util.Observer;

public class ColorWolf implements Observer {

	public void update(Observable o, Object arg) {
		SexyGirl subject = (SexyGirl)o;
		if(subject != null){
			System.out.println("Oh, I am color wolf, I see.");
		}
	}

	
	
}
