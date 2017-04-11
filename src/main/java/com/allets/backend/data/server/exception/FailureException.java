package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;

public class FailureException extends RestRuntimeException {

    private static final long serialVersionUID = 3561948328947528192L;


    public FailureException(HttpStatusCode httpStatusCode, HttpStatusMessage httpStatusMessage) {
        init("backend.api.server.FailureException", httpStatusCode, httpStatusMessage);
    }

}
