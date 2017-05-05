package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.RestRuntimeException;
import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;


/**
 * Un authorizedException
 */
public class UnAuthorizedException extends RestRuntimeException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8545222837958494883L;

	/**
	 *
	 *
     */
	public UnAuthorizedException() {
		init("backend.api.server.UnAuthorizedException", HttpStatusCode.SC_401, HttpStatusMessage.SC_401);
	}
}
