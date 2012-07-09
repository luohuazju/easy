package com.sillycat.easytalker.model;

public class AjaxMessage {
	
	private boolean success = true;

	private String errorCode;

	private String errorMessage;

	private String content;
	
	private int echo = 200;
	
	public AjaxMessage(){
		
	}
	
	public AjaxMessage(boolean success, String content){
		this.success = success;
		this.content = content;
	}
	
	public int getEcho() {
		return echo;
	}

	public void setEcho(int echo) {
		this.echo = echo;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
