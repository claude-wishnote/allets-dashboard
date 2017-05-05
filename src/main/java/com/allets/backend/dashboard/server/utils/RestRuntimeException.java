package com.allets.backend.dashboard.server.utils;


// TODO: Auto-generated Javadoc


/**
 * The Class AbstractException.
 */
public class RestRuntimeException extends RuntimeException{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2230489162206490697L;
	
	
	/** The error message. */
	ErrorMessage errorMessage = new ErrorMessage();

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

  	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void init(String messageKey, HttpStatusCode httpStatusCode, HttpStatusMessage httpStatusMessage) {
		this.errorMessage.setMessageKey(messageKey);
		this.errorMessage.setHttpStatusCode(httpStatusCode);
		this.errorMessage.setHttpStatusMessage(httpStatusMessage);
  	 	}

}
