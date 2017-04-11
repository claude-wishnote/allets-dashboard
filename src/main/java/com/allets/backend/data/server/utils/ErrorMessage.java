package com.allets.backend.data.server.utils;


/**
 * 실제 클라이언트에게 전송될 메세지(ResponseBody). Json 형태로 리턴 한다.
 */
public class ErrorMessage {

	/** The Constant DEFAULT_ERROR_MESSAGE_KEY. */
	final static String DEFAULT_ERROR_MESSAGE_KEY = "exception.default";
	
	/** The message key. */
	String messageKey = DEFAULT_ERROR_MESSAGE_KEY;

	HttpStatusCode httpStatusCode = HttpStatusCode.SC_500;

	HttpStatusMessage httpStatusMessage = HttpStatusMessage.SC_500;

	public String getMessageKey() {
		return messageKey;
	}
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public HttpStatusCode getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public HttpStatusMessage getHttpStatusMessage() {
		return httpStatusMessage;
	}

	public void setHttpStatusMessage(HttpStatusMessage httpStatusMessage) {
		this.httpStatusMessage = httpStatusMessage;
	}
}
