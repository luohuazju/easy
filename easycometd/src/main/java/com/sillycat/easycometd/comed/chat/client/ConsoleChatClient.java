package com.sillycat.easycometd.comed.chat.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cometd.bayeux.Channel;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.LongPollingTransport;

public class ConsoleChatClient {

	private static String nickname = "karl";
	private static BayeuxClient client;
	private final ChatListener chatListener = new ChatListener();
	private final MembersListener membersListener = new MembersListener();

	public static void main(String[] args) throws IOException {
		ConsoleChatClient client = new ConsoleChatClient();
		client.go();
	}

	public void go() {
		String defaultURL = "http://localhost:8080/easycometd/cometd";
		String url = defaultURL;

		client = new BayeuxClient(url, LongPollingTransport.create(null));
		client.getChannel(Channel.META_HANDSHAKE).addListener(
				new InitializerListener());
		client.getChannel(Channel.META_CONNECT).addListener(
				new ConnectionListener());

		client.handshake();
		boolean success = client.waitFor(1000, BayeuxClient.State.CONNECTED);
		if (!success) {
			System.err.printf("Could not handshake with server at %s%n", url);
			return;
		}

		String text = "hello";

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user", nickname);
		data.put("chat", text);
		client.getChannel("/chat/demo").publish(data);

		client.disconnect(1000);
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

	private void connectionEstablished() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user", nickname);
		data.put("room", "/chat/demo");
		client.getChannel("/service/members").publish(data);
	}

	private void connectionClosed() {
		System.err.printf("system: Connection to Server Closed%n");
	}

	private void connectionBroken() {
		System.err.printf("system: Connection to Server Broken%n");
	}

	private class InitializerListener implements
			ClientSessionChannel.MessageListener {
		public void onMessage(ClientSessionChannel channel, Message message) {
			if (message.isSuccessful()) {
				initialize();
			}
		}
	}

	private class ConnectionListener implements
			ClientSessionChannel.MessageListener {
		private boolean wasConnected;
		private boolean connected;

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
	}

	private class ChatListener implements ClientSessionChannel.MessageListener {
		public void onMessage(ClientSessionChannel channel, Message message) {
			Map<String, Object> data = message.getDataAsMap();
			String fromUser = (String) data.get("user");
			String text = (String) data.get("chat");
			System.out.printf("%s: %s%n", fromUser, text);
		}
	}

	private class MembersListener implements
			ClientSessionChannel.MessageListener {
		@SuppressWarnings("rawtypes")
		public void onMessage(ClientSessionChannel channel, Message message) {
			Object data = message.getData();
			Object[] members = data instanceof List ? ((List) data).toArray()
					: (Object[]) data;
			System.out.printf("Members: %s%n", Arrays.asList(members));
		}
	}
}
