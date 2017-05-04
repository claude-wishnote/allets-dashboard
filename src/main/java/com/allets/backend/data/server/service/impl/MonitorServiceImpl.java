package com.allets.backend.data.server.service.impl;


import com.allets.backend.data.server.exception.FailureException;
import com.allets.backend.data.server.utils.JsonUtil;
import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.entity.common.Monitor;
import com.allets.backend.data.server.repository.common.MonitorRepository;
import com.allets.backend.data.server.service.MonitorService;
import com.allets.backend.data.server.utils.EncryptUtil;
import com.allets.backend.data.server.utils.HttpStatusCode;
import com.allets.backend.data.server.utils.HttpStatusMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {

    final Logger log = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @Autowired
    MonitorRepository monitorRepository;

    @Override
    public Monitor selectMonitorByName(String name) throws Exception {
        return monitorRepository.findByName(name);
    }

    @Override
    public List<Monitor> selectMonitorList() throws Exception {
        List<Sort.Order> orders = new ArrayList<>();
        Sort.Order level = new Sort.Order(Sort.Direction.ASC, "level");
        Sort.Order status = new Sort.Order(Sort.Direction.ASC, "status");
        orders.add(level);
        orders.add(status);
        Sort sort = new Sort(orders);
        return monitorRepository.findAll(sort);
    }

    @Override
    public Monitor insertMonitor(Monitor monitor) throws Exception {
        Monitor existMonitor = monitorRepository.findByName(monitor.getName());
        if (existMonitor != null)
            throw new FailureException(HttpStatusCode.SC_500, HttpStatusMessage.SC_500);
        return monitorRepository.save(monitor);
    }

    @Override
    public Integer modifyMonitorForceResetPassword(Monitor monitor) throws Exception {
        if (monitor.getPassword().equals(null))
            monitor.setPassword(EncryptUtil.encryptMd5String(Const.DEFAULT_PASSWORD));
        return monitorRepository.updateMonitor(monitor);
    }

    @Override
    public void updateMonitor(Monitor monitor) throws Exception {
        Monitor currentMonitor = monitorRepository.findByMonitorId(monitor.getMonitorId());

        //password
        if (StringUtils.isNotBlank(monitor.getOldPassword()) && StringUtils.isNotBlank(monitor.getPassword())) {
            log.info(JsonUtil.marshallingJson(monitor));
            if (currentMonitor.getPassword().equals(monitor.getOldPassword())) {
                currentMonitor.setPassword(monitor.getPassword());
            } else {
                //TODO
                throw new FailureException(HttpStatusCode.SC_410, HttpStatusMessage.SC_410);
            }
        }

        //status
        if (StringUtils.isNotBlank(monitor.getStatus())) {
            currentMonitor.setStatus(monitor.getStatus());
        }
        //Level
        if (StringUtils.isNotBlank(monitor.getLevel())) {
            currentMonitor.setLevel(monitor.getLevel());
        }
        //udate
        currentMonitor.setUdate(new Date());

        monitorRepository.updateMonitor(currentMonitor);
    }

    @Override
    public void updateMonitorLastLoginTime(Long monitorId) throws Exception {
        monitorRepository.updateMonitorLastLoginTime(monitorId);
    }
}
