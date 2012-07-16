package com.sillycat.easynio.plugins.mina.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.sillycat.easynio.plugins.mina.businesshandlers.CalculateClientHandler;

public class MinaClientSupport {

	private IoHandler handler = null;

	private String host;

	private IoSession session = null;

	private int port;

	public boolean send(Object message) {
		SocketAddress address = new InetSocketAddress(host, port);
		NioSocketConnector connector = new NioSocketConnector();
		try {
			if (session == null) {
				connector.getFilterChain().addLast("mdc",
						new MdcInjectionFilter());
				connector.setHandler(handler);
				ConnectFuture future1 = connector.connect(address);
				future1.awaitUninterruptibly();
				if (!future1.isConnected()) {
					return false;
				}
				session = future1.getSession();
			}
			session.write(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void close() {
		if (session != null) {
			if (session.isConnected()) {
				// Wait until the chat ends.
				session.getCloseFuture().awaitUninterruptibly();
			}
			session.close(true);
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setHandler(IoHandler handler) {
		this.handler = handler;
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Application Test:");
		MinaClientSupport client = new MinaClientSupport();
		CalculateClientHandler handler = new CalculateClientHandler();
		client.setHandler(handler);
		client.setHost("localhost");
		client.setPort(12345);
		String msg = "1+3*6";
		client.send(msg);
		System.out.println("Client Send: " + msg);
		client.send("quit");
		client.close();
	}

}
