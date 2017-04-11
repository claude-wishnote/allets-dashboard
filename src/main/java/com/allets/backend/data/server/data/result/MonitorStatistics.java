package com.allets.backend.data.server.data.result;

import java.util.Date;

/**
 * Created by claude on 2016/1/31.
 */
public class MonitorStatistics {
    private Long monitorId;
    private String name;
    private int deleteCommentsCount;
    private int sendAlertCount;
    private int accountHoldCount;
    private int accountDeleteCount;
    private int accountResetCount;
    private Date lastAccessDate;

    public MonitorStatistics() {
        super();
    }

    public MonitorStatistics(Long monitorId, String name, int deleteCommentsCount, int sendAlertCount, int accountHoldCount, int accountDeleteCount, int accountResetCount, Date lastAccessDate) {
        this.monitorId = monitorId;
        this.name = name;
        this.deleteCommentsCount = deleteCommentsCount;
        this.sendAlertCount = sendAlertCount;
        this.accountHoldCount = accountHoldCount;
        this.accountDeleteCount = accountDeleteCount;
        this.accountResetCount = accountResetCount;
        this.lastAccessDate = lastAccessDate;
    }

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeleteCommentsCount() {
        return deleteCommentsCount;
    }

    public void setDeleteCommentsCount(int deleteCommentsCount) {
        this.deleteCommentsCount = deleteCommentsCount;
    }

    public int getSendAlertCount() {
        return sendAlertCount;
    }

    public void setSendAlertCount(int sendAlertCount) {
        this.sendAlertCount = sendAlertCount;
    }

    public int getAccountHoldCount() {
        return accountHoldCount;
    }

    public void setAccountHoldCount(int accountHoldCount) {
        this.accountHoldCount = accountHoldCount;
    }

    public int getAccountDeleteCount() {
        return accountDeleteCount;
    }

    public void setAccountDeleteCount(int accountDeleteCount) {
        this.accountDeleteCount = accountDeleteCount;
    }

    public int getAccountResetCount() {
        return accountResetCount;
    }

    public void setAccountResetCount(int accountResetCount) {
        this.accountResetCount = accountResetCount;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    @Override
    public String toString() {
        return "MonitorStatistics{" +
                "monitorId=" + monitorId +
                ", name='" + name + '\'' +
                ", deleteCommentsCount=" + deleteCommentsCount +
                ", sendAlertCount=" + sendAlertCount +
                ", accountHoldCount=" + accountHoldCount +
                ", accountDeleteCount=" + accountDeleteCount +
                ", accountResetCount=" + accountResetCount +
                ", lastAccessDate=" + lastAccessDate +
                '}';
    }
}
