package com.allets.backend.dashboard.server.facade.impl;

import com.allets.backend.dashboard.server.data.dto.MonitorDTO;
import com.allets.backend.dashboard.server.entity.common.Monitor;
import com.allets.backend.dashboard.server.facade.SecurityFacade;
import com.allets.backend.dashboard.server.service.MonitorService;
import com.google.common.base.Strings;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class SecurityFacadeImpl implements SecurityFacade {

    @Autowired
    MonitorService monitorService;

    @Override
    public MonitorDTO getAuthenticatedMonitor() throws Exception {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        final String USERNAME = Objects.toString(principal, "");
        if (Strings.isNullOrEmpty(USERNAME)) {
            return null;
        } else {

            Monitor monitor = monitorService.selectMonitorByName(USERNAME);
            MonitorDTO monitorDTO = new MonitorDTO(monitor);
            return monitorDTO;
        }
    }
}
