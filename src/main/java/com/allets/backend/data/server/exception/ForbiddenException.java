package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;


/**
 * forbidden
 */
public class ForbiddenException extends RestRuntimeException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8545222837958494884L;

	/**
	 *
	 *
     */
	public ForbiddenException() {
		init("backend.api.server.ForbiddenException", HttpStatusCode.SC_403, HttpStatusMessage.SC_403);
	}
	
}
