package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;


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
