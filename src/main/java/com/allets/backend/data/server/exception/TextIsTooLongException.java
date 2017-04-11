package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.HttpStatusMessage;
import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;


public class TextIsTooLongException extends RestRuntimeException {

    public TextIsTooLongException() {
        init("backend.api.server.TextIsTooLongException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
    }

}
