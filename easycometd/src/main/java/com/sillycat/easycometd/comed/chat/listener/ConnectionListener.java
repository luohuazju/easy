package com.sillycat.easycometd.comed.chat.listener;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;

public class ConnectionListener implements ClientSessionChannel.MessageListener {

	private boolean wasConnected;
	
	private boolean connected;
	
	private String nickname = "karl";
	
	private BayeuxClient client;
	
	public ConnectionListener(String nickname, BayeuxClient client){
		this.nickname = nickname;
		this.client = client;
	}
	
	private void connectionClosed() {
		System.out.printf("system: Connection to Server Closed%n");
	}

	public void onMessage(ClientSessionChannel channel, Message message) {
		if (client.isDisconnected()) {
			connected = false;
			connectionClosed();
			return;
		}

		wasConnected = connected;
		connected = message.isSuccessful();
		if (!wasConnected && connected) {
			connectionEstablished();
		} else if (wasConnected && !connected) {
			connectionBroken();
		}
	}
	
	private void connectionEstablished() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user", nickname);
		data.put("room", "/chat/demo");
		client.getChannel("/service/members").publish(data);
	}
	
	private void connectionBroken() {
		System.out.printf("system: Connection to Server Broken%n");
	}

}
