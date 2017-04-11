package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;


public class NotSupportActionException extends RestRuntimeException {

	public NotSupportActionException() {
		init("backend.api.server.NotSupportActionException", HttpStatusCode.SC_404, HttpStatusMessage.SC_404);
	}

}
