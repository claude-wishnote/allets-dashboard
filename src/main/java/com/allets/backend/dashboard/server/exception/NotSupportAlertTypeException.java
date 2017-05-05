package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;


public class NotSupportAlertTypeException extends RestRuntimeException {

	public NotSupportAlertTypeException() {
		init("backend.api.server.NotSupportAlertTypeException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
	}

}
