package com.sillycat.easytalker.plugins.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {

	public static void main(String[] args) {
		try {
			IHello rhello = (IHello) Naming.lookup("//localhost:9813/rhello");
			System.out.println(rhello.helloWorld());
			System.out.println(rhello.sayHelloToSomeBody("sillycat"));
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
