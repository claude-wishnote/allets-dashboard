package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;
import com.allets.backend.data.server.utils.RestRuntimeException;

/**
 * Created by claude on 2016/1/24.
 */
public class ParameterTypeErrorException extends RestRuntimeException {
    public ParameterTypeErrorException() {
        init("backend.api.server.ParameterTypeErrorException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
    }
}
