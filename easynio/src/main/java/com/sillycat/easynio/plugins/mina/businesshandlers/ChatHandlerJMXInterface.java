package com.sillycat.easynio.plugins.mina.businesshandlers;

public interface ChatHandlerJMXInterface {
	
	public boolean isChatUser(String name);
	
	public int getNumberOfUsers();
	
	public void kick(String name);

}
