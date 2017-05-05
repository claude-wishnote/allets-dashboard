package com.allets.backend.dashboard.server.facade;

import com.allets.backend.dashboard.server.data.dto.MonitorDTO;

public interface SecurityFacade {

    MonitorDTO getAuthenticatedMonitor() throws Exception;
}
