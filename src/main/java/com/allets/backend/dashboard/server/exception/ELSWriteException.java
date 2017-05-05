package com.allets.backend.dashboard.server.exception;

import com.allets.backend.dashboard.server.utils.RestRuntimeException;
import com.allets.backend.dashboard.server.utils.HttpStatusCode;
import com.allets.backend.dashboard.server.utils.HttpStatusMessage;

/**
 * Created by claude on 2016/1/12.
 */
public class ELSWriteException  extends RestRuntimeException {
    public ELSWriteException() {
        init("backend.api.server.ELSWriteException", HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
    }
 }
