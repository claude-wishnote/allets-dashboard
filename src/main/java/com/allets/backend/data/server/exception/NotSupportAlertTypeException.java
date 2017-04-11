package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;


public class NotSupportAlertTypeException extends RestRuntimeException {

	public NotSupportAlertTypeException() {
		init("backend.api.server.NotSupportAlertTypeException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
	}

}
