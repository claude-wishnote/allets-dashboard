package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.data.result.MonitorStatistics;
import com.allets.backend.data.server.entity.common.Monitor;
import com.allets.backend.data.server.facade.MonitorFacade;
import com.allets.backend.data.server.service.MonitorService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jack on 2015/9/8.
 */
@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class MonitorFacadeImpl implements MonitorFacade {

    @Autowired
    MonitorService monitorService;

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<Monitor> findMonitorAll() throws Exception {
        return monitorService.selectMonitorList();
    }

    /**
     *
     * @param monitor
     * @return
     * @throws Exception
     */
    @Transactional(value = "commonTxManager")
    @Override
    public Monitor createMonitor(Monitor monitor) throws Exception {
        return monitorService.insertMonitor(monitor);
    }

    /**
     *
     * @param monitor
     * @throws Exception
     */
    @Transactional(value = "commonTxManager")
    @Override
    public void modifyMonitor(Monitor monitor) throws Exception {
        monitorService.updateMonitor(monitor);
    }

    @Transactional(value = "commonTxManager")
    @Override
    public void modifyMonitorForceResetPassword(Monitor monitor) throws Exception {
        monitorService.modifyMonitorForceResetPassword(monitor);

    }

    @Override
    public List<MonitorStatistics> findMonitorHandleHistory(String monitorIds, String q) throws Exception {
        return monitorService.selectMonitorHandleHistory(monitorIds,q);
    }

    @Transactional(value = "commonTxManager")
    @Override
    public void modifyMonitorLastLoginTime(Long monitorId) throws Exception {
        monitorService.updateMonitorLastLoginTime(monitorId);
    }

    @Override
    public HSSFWorkbook findStatistics(String q) throws Exception {
        return monitorService.selectStatistics(q);
    }
}
