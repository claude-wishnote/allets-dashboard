package com.allets.backend.data.server.service;

import com.allets.backend.data.server.entity.common.Monitor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MonitorService {

    Monitor selectMonitorByName(String name) throws Exception;


    /**
     *select slang word list
     * @return
     * @throws Exception
     */
    List<Monitor> selectMonitorList() throws Exception;

    /**
     *
     * @param monitor
     * @return
     * @throws Exception
     */
    Monitor insertMonitor(Monitor monitor) throws Exception;

    /**
     *
     * @param monitor
     * @throws Exception
     */
    void updateMonitor(Monitor monitor) throws Exception;

    Integer modifyMonitorForceResetPassword(Monitor monitor) throws Exception;

    void updateMonitorLastLoginTime(Long monitorId) throws Exception;

}
