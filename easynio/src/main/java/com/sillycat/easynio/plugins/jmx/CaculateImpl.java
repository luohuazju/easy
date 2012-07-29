package com.sillycat.easynio.plugins.jmx;

public class CaculateImpl implements CaculateInterface{

	public int add(int x, int y) {
		return x + y;
	}

	public String echo(String message) {
		return "Hello, it is message from JMX = " + message;
	}

	public String securit() {
		return "forbidden!";
	}
	
	

}
