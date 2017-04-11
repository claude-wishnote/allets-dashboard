/*
 * 
 */
package com.allets.backend.data.server.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: Auto-generated Javadoc

/**
 * REST 호출시 표준 reponse 오브젝트.
 * 해당 오브젝트는 자동으로 Json으로 마샬링 된다.
 *
 *
 * {
 	“status” : “401”,
 	“message” : “login failure”,
 	"errorMessage" : "you didn't get authentication token. please login",
 	“errorCode” : “A30201”
  }


 */
public class RestResponseExceptionObject {

	/** HTTP STATUS 값. */
	int status = 500;

	/* HTTP STATUS 메세지*/
	String httpMessage = HttpStatusMessage.SC_500.toString();

	/* properties에 정의된 메세지 */
	String errorMessage = "System Error!!";

	String errorCode = "U999999";

	@JsonIgnore
	String messageKey = "";


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getHttpMessage() {
		return httpMessage;
	}

	public void setHttpMessage(String httpMessage) {
		this.httpMessage = httpMessage;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
}
