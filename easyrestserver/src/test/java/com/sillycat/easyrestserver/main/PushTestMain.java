package com.sillycat.easyrestserver.main;

import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.transmission.PushQueue;

import org.json.JSONException;

public class PushTestMain {

	private static final String DEFAULT_MESSAGE = "Wow, sillycat.";

	private static final String P12_FILE = "/Users/carl/work/easy/easyrestserver/src/main/resources/EasyiOSSampleKey_final.p12";

	private static final String DEFAULT_PASSWORD = "kaishi117";

	private static final String DEFAULT_TOKEN = "2fd9bd9863266b3c2456c6b77d1a139f082c66da6933490ad958a3efee65fa23";

	public static void main(String[] args) {
		// sendBasic();
		sendQueue();
		// sendMultiple();
	}
	
	private static void sendMultiple(){
		PushNotificationPayload payload = PushNotificationPayload.complex();
		try {
			payload.addAlert(DEFAULT_MESSAGE);
			payload.addSound("default");
			payload.addBadge(1);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		int threads = 2;

		List<Device> devices = new ArrayList<Device>();
		try {
			devices.add(new BasicDevice(DEFAULT_TOKEN));
		} catch (InvalidDeviceTokenFormatException e1) {
			e1.printStackTrace();
		}
		try {
			List<PushedNotification> notifications = Push.payload(payload,
					P12_FILE, DEFAULT_PASSWORD, false, threads, devices);
			System.out.println(notifications);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sendQueue() {
		PushNotificationPayload payload = PushNotificationPayload.complex();
		try {
			payload.addAlert(DEFAULT_MESSAGE);
			payload.addSound("default");
			payload.addBadge(1);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		int threads = 2;
		PushQueue queue = null;
		try {
			queue = Push.queue(P12_FILE, DEFAULT_PASSWORD, false, threads);
			queue.start();
			queue.add(payload, DEFAULT_TOKEN);
		} catch (KeystoreException e) {
			e.printStackTrace();
		} catch (InvalidDeviceTokenFormatException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(10* 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void sendBasic() {
		try {
			Push.alert(DEFAULT_MESSAGE, P12_FILE, DEFAULT_PASSWORD, false,
					DEFAULT_TOKEN);
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (KeystoreException e) {
			e.printStackTrace();
		}
	}

}
