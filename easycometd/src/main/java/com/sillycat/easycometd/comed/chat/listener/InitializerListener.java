package com.sillycat.easycometd.comed.chat.listener;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;

public class InitializerListener implements
		ClientSessionChannel.MessageListener {

	private String nickname;

	private BayeuxClient client;

	private ChatListener chatListener;

	private MembersListener membersListener;

	public InitializerListener(String nickname, BayeuxClient client,
			ChatListener chatListener, MembersListener membersListener) {
		this.nickname = nickname;
		this.client = client;
		this.chatListener = chatListener;
		this.membersListener = membersListener;
	}

	public void onMessage(ClientSessionChannel channel, Message message) {
		if (message.isSuccessful()) {
			initialize();
		}
	}

	private void initialize() {
		client.batch(new Runnable() {
			public void run() {
				ClientSessionChannel chatChannel = client
						.getChannel("/chat/demo");
				chatChannel.unsubscribe(chatListener);
				chatChannel.subscribe(chatListener);

				ClientSessionChannel membersChannel = client
						.getChannel("/chat/members");
				membersChannel.unsubscribe(membersListener);
				membersChannel.subscribe(membersListener);

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("user", nickname);
				data.put("membership", "join");
				data.put("chat", nickname + " has joined");
				chatChannel.publish(data);
			}
		});
	}

}
