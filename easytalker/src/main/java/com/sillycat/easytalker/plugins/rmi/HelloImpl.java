package com.sillycat.easytalker.plugins.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements IHello {

	private static final long serialVersionUID = 6318519261707859826L;

	public HelloImpl() throws RemoteException {
	}

	public String helloWorld() throws RemoteException {
		return "Hello World!";
	}

	public String sayHelloToSomeBody(String someBodyName)
			throws RemoteException {
		return "Hello, nice to meet you, " + someBodyName + "!";
	}
}
