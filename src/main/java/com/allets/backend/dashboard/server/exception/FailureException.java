package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;

public class FailureException extends RestRuntimeException {

    private static final long serialVersionUID = 3561948328947528192L;


    public FailureException(HttpStatusCode httpStatusCode, HttpStatusMessage httpStatusMessage) {
        init("backend.api.server.FailureException", httpStatusCode, httpStatusMessage);
    }

}
