package com.sillycat.easycometd.comed.chat.listener;

import java.util.Arrays;
import java.util.List;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

public class MembersListener implements ClientSessionChannel.MessageListener {

	@SuppressWarnings("rawtypes")
	public void onMessage(ClientSessionChannel channel, Message message) {
		Object data = message.getData();
		Object[] members = data instanceof List ? ((List) data).toArray()
				: (Object[]) data;
		System.out.printf("Members: %s%n", Arrays.asList(members));
	}

}
