package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;


public class NotFountParentRuleException extends RestRuntimeException {

	public NotFountParentRuleException() {
		init("backend.api.server.NotFountParentRuleException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
	}

}
