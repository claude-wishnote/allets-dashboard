package com.allets.backend.data.server.exception;

import com.allets.backend.data.server.utils.RestRuntimeException;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;

/**
 * Created by claude on 2016/1/12.
 */
public class ELSWriteException  extends RestRuntimeException {
    public ELSWriteException() {
        init("backend.api.server.ELSWriteException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
    }
 }
