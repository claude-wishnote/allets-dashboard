package com.allets.backend.dashboard.server.facade;

 import com.allets.backend.dashboard.server.entity.common.Monitor;

 import java.util.List;

/**
 * Created by jack on 2015/9/8.
 */
public interface MonitorFacade {

    /**
     * @return
     * @throws Exception
     */
    List<Monitor> findMonitorAll() throws Exception;

    /**
     * @param monitor
     * @return
     * @throws Exception
     */
    Monitor createMonitor(Monitor monitor) throws Exception;


    /**
     * @param monitor
     * @throws Exception
     */
    void modifyMonitor(Monitor monitor) throws Exception;

    void modifyMonitorForceResetPassword(Monitor monitor) throws Exception;

    void modifyMonitorLastLoginTime(Long monitorId) throws Exception;

}
