package com.allets.backend.dashboard.server.repository.common;

import com.allets.backend.dashboard.server.entity.common.Monitor;

/**
 * Created by claude on 2016/1/31.
 */
public interface MonitorRepositoryCustum {

    public void updateMonitorLastLoginTime(Long monitorId) throws Exception;

    public Integer updateMonitor(Monitor monitor) throws Exception;
}
