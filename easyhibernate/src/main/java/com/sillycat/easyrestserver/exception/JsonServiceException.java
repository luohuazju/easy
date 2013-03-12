package com.sillycat.easyrestserver.exception;

public class JsonServiceException extends Exception {

	private static final long serialVersionUID = -2949102718021710130L;

	private String errorCode;
	private String errorMessage;

	public JsonServiceException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
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

}
