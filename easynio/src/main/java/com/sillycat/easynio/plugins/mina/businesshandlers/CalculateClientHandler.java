package com.sillycat.easynio.plugins.mina.businesshandlers;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;

public class CalculateClientHandler extends IoHandlerAdapter {

	private static final IoFilter LOGGING_FILTER = new LoggingFilter();

	private static final IoFilter CODEC_FILTER = new ProtocolCodecFilter(
			new TextLineCodecFactory());

	public void sessionCreated(IoSession session) throws Exception {
		session.getFilterChain().addLast("codec", CODEC_FILTER);
		session.getFilterChain().addLast("logger", LOGGING_FILTER);
	}

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg = (String) message;
		System.out.println("Client Received: " + msg);
	}

}
