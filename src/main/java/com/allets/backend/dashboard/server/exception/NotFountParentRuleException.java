package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;


public class NotFountParentRuleException extends RestRuntimeException {

	public NotFountParentRuleException() {
		init("backend.api.server.NotFountParentRuleException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
	}

}
