package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;


/**
 * notfound user.
 */
public class NotFoundUserException extends RestRuntimeException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8545222837958494881L;

	/**
	 * Instantiates a new not found user.
	 *
	 * @param messageKey the message key
	 * @param httpStatus the http status
     */
	public NotFoundUserException() {
		init("backend.api.server.NotFoundUserException", HttpStatusCode.SC_410, HttpStatusMessage.SC_410);
	}
	
}
