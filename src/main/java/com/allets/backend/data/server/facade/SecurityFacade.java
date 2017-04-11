package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.data.dto.MonitorDTO;

public interface SecurityFacade {

    MonitorDTO getAuthenticatedMonitor() throws Exception;
}
