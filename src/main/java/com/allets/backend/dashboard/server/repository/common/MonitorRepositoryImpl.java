package com.allets.backend.dashboard.server.repository.common;

import com.allets.backend.dashboard.server.entity.common.Monitor;
import com.allets.backend.dashboard.server.entity.common.QMonitor;
import com.allets.backend.dashboard.server.utils.AwsUpload;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by claude on 2016/1/31.
 */
public class MonitorRepositoryImpl implements MonitorRepositoryCustum {
    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;
    @Autowired
    AwsUpload awsUpload;


    public Boolean checkDate(String date) {
        return date != null && !date.isEmpty() & !date.equals("Invalid Date");
    }

    @Override
    public void updateMonitorLastLoginTime(Long monitorId) throws Exception {
        QMonitor qMonitor = QMonitor.monitor;
        JPAUpdateClause qMonitorUpdate = new JPAUpdateClause(entityManager, qMonitor);
        qMonitorUpdate.where(qMonitor.monitorId.eq(monitorId))
                .set(qMonitor.lastLoginTime, new Date());
        qMonitorUpdate.execute();
    }

    @Override
    public Integer updateMonitor(Monitor monitor) throws Exception {
        QMonitor qMonitor = QMonitor.monitor;
        JPAUpdateClause QMonitorUpdate = new JPAUpdateClause(entityManager, qMonitor);
        Integer num = 0;
        QMonitorUpdate.where(qMonitor.monitorId.eq(monitor.getMonitorId()));
        if (monitor.getLevel() != null) {
            QMonitorUpdate.set(qMonitor.level, monitor.getLevel());
        }
        if (monitor.getCdate() != null) {
            QMonitorUpdate.set(qMonitor.cdate, monitor.getCdate());
        }
        if (monitor.getName() != null) {
            QMonitorUpdate.set(qMonitor.name, monitor.getName());
        }
        if (monitor.getPassword() != null) {
            QMonitorUpdate.set(qMonitor.password, monitor.getPassword());
        }
        if (monitor.getStatus() != null) {
            QMonitorUpdate.set(qMonitor.status, monitor.getStatus());
        }
        if (monitor.getUdate() != null) {
            QMonitorUpdate.set(qMonitor.udate, monitor.getUdate());
        }
        num = Long.valueOf(QMonitorUpdate.execute()).intValue();
        return num;
    }
}
