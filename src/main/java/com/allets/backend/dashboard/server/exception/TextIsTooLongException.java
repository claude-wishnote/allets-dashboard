package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;


public class TextIsTooLongException extends RestRuntimeException {

    public TextIsTooLongException() {
        init("backend.api.server.TextIsTooLongException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
    }

}
