package com.sillycat.easynio.plugins.mina.observer;

public class WolfTest {
	
	
	public static void main(String[] args) {
		SexyGirl girl = new SexyGirl();
		ColorWolf wolf = new ColorWolf();
		Sage sage = new Sage();
		
		girl.addObserver(wolf);
		girl.addObserver(sage);
		girl.dance();
	}

}
