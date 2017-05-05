package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;


public class NotSupportActionException extends RestRuntimeException {

	public NotSupportActionException() {
		init("backend.api.server.NotSupportActionException", HttpStatusCode.SC_404, HttpStatusMessage.SC_404);
	}

}
