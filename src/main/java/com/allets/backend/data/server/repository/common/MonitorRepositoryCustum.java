package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.MonitorStatistics;
import com.allets.backend.data.server.entity.common.Monitor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
 * Created by claude on 2016/1/31.
 */
public interface MonitorRepositoryCustum {
    public List<MonitorStatistics> findUserHandleHistory(String monitorIds, String q) throws Exception;

    public void updateMonitorLastLoginTime(Long monitorId) throws Exception;

    public HSSFWorkbook findStatistics(String q) throws Exception;

    public Integer updateMonitor(Monitor monitor) throws Exception;
}
