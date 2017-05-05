package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;


/**
 * Invalid parameter.
 */
public class NoStatisticsException extends RestRuntimeException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8545222837958494882L;

	/**
	 * Instantiates a new not found user.
	 *
     */
	public NoStatisticsException() {
		init("backend.api.server.NoStatisticsException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
	}

}
