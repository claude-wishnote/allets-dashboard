package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.HttpStatusMessage;
import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;


/**
 * Invalid parameter.
 */
public class BadRequestException extends RestRuntimeException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8545222837958494882L;

	/**
	 * Instantiates a new not found user.
	 *
     */
	public BadRequestException( ) {
		init("backend.api.server.BadRequestException", HttpStatusCode.SC_400, HttpStatusMessage.SC_400);
	}

}
