package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.facade.MonitorFacade;
import com.allets.backend.data.server.utils.JsonUtil;
import com.allets.backend.data.server.config.RootApplicationContextConfig;
import com.allets.backend.data.server.entity.common.Monitor;
import com.allets.backend.data.server.utils.EncryptUtil;
import com.allets.backend.data.server.consts.Level;
import com.allets.backend.data.server.consts.Status;
import com.allets.backend.data.server.consts.Const;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by jack on 2015/9/8.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@TransactionConfiguration(transactionManager = "commonTxManager", defaultRollback = true)
@Transactional
public class MonitorFacadeImplTest {

    final Logger log = LoggerFactory.getLogger(EmpFacadeImplTest.class);

    @Autowired
    MonitorFacade monitorFacade;

    @Test
    public void findMonitorAll() throws Exception {
        List<Monitor> monitors = monitorFacade.findMonitorAll();
        assertNotNull(monitors);
        log.info(JsonUtil.marshallingJson(monitors));
    }

    @Test
    public void createMonitor() throws Exception {
        Monitor monitor = new Monitor();
        monitor.setName("Jason");
        monitor.setLevel(Level.NORMAL);
        monitor.setStatus(Status.MonitorStatus.ACTV);
        monitor.setUdate(new Date());
        monitor.setPassword(EncryptUtil.encryptMd5String(Const.DEFAULT_PASSWORD));
        Monitor newMonitor = monitorFacade.createMonitor(monitor);
        assertNotNull(newMonitor);
        log.info(JsonUtil.marshallingJson(newMonitor));
    }

    @Test
    public void modifyMonitor() throws Exception {
        Monitor monitor = new Monitor();
        monitor.setMonitorId(3);
        monitor.setPassword("81dc9bdb52d04dc20036dbd8313ed055");
        monitor.setOldPassword("81dc9bdb52d04dc20036dbd8313ed055");
        monitor.setLevel("ADMIN");
        monitor.setStatus("DEL");
        monitorFacade.modifyMonitor(monitor);
    }
}
