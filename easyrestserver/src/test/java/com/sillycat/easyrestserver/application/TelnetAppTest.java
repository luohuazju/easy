package com.sillycat.easyrestserver.application;

import java.io.IOException;
import de.mud.telnet.TelnetWrapper;

public class TelnetAppTest {

	public static void main(String[] args) throws IOException {
		 TelnetWrapper telnet = new TelnetWrapper();
		 telnet.connect("localhost", 5554);
		 //telnet.send("geo fix " + longitude + " " + latitude);
		 telnet.send("help");
		 telnet.disconnect();
	}

}
