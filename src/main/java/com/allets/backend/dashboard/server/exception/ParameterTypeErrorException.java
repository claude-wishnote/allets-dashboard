package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;
import com.allets.backend.dashboard.server.utils.RestRuntimeException;

/**
 * Created by claude on 2016/1/24.
 */
public class ParameterTypeErrorException extends RestRuntimeException {
    public ParameterTypeErrorException() {
        init("backend.api.server.ParameterTypeErrorException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
    }
}
