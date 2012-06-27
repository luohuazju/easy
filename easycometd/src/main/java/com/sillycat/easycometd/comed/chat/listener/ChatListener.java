package com.sillycat.easycometd.comed.chat.listener;

import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

public class ChatListener implements ClientSessionChannel.MessageListener {

	public void onMessage(ClientSessionChannel channel, Message message) {
		Map<String, Object> data = message.getDataAsMap();
		String fromUser = (String) data.get("user");
		String text = (String) data.get("chat");
		System.out.printf("Message ----- %s: %s%n", fromUser, text);
	}

}
