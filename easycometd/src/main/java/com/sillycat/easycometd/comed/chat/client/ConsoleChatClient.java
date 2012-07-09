package com.sillycat.easycometd.comed.chat.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Channel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.LongPollingTransport;

import com.sillycat.easycometd.comed.chat.listener.ChatListener;
import com.sillycat.easycometd.comed.chat.listener.ConnectionListener;
import com.sillycat.easycometd.comed.chat.listener.InitializerListener;
import com.sillycat.easycometd.comed.chat.listener.MembersListener;

public class ConsoleChatClient {

	private static String nickname = "karl";

	private static BayeuxClient client;

	private final ChatListener chatListener = new ChatListener();

	private final MembersListener membersListener = new MembersListener();

	public static void main(String[] args) throws IOException,
			InterruptedException {
		ConsoleChatClient client = new ConsoleChatClient();
		client.go();
	}

	public void go() throws InterruptedException {
		String defaultURL = "http://localhost:8080/easycometd/cometd";
		String url = defaultURL;

		client = new BayeuxClient(url, LongPollingTransport.create(null));
		client.getChannel(Channel.META_HANDSHAKE).addListener(
				new InitializerListener(nickname, client, chatListener,
						membersListener));
		client.getChannel(Channel.META_CONNECT).addListener(
				new ConnectionListener(nickname, client));

		client.handshake();

		boolean success = client.waitFor(2000, BayeuxClient.State.CONNECTED);
		if (!success) {
			System.err.printf("Could not handshake with server at %s%n", url);
			return;
		}

		String text = "hello,where is the book";

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user", nickname);
		data.put("chat", text);
		client.getChannel("/chat/demo").publish(data);

		Map<String, Object> pdata = new HashMap<String, Object>();
		text = "hello,where is the money";
		pdata.put("chat", text);
		pdata.put("room", "/chat/demo");
		pdata.put("user", "karl");
		pdata.put("peer", "sillycat");
		client.getChannel("/service/privatechat").publish(pdata);

		for (int i = 0; i < 10; i++) {
			Thread.sleep(5000);
		}

		data = new HashMap<String, Object>();
		data.put("user", nickname);
		data.put("membership", "leave");
		data.put("chat", nickname + " has left");
		client.getChannel("/chat/demo").publish(data);

		client.getChannel("/service/privatechat").release();
		client.getChannel("/chat/demo").release();
		client.disconnect(3000);
	}

}
