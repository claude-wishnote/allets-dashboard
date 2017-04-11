package com.allets.backend.data.server.data.result;

import java.util.Date;

/**
 * Created by claude on 2016/1/31.
 */
public class UserHandleHistoryResult {
    private Long id;
    private Long uid;
    private String handleResult;
    private Long monitorId;
    private Date cdate;

    public UserHandleHistoryResult(Long id, Long uid, String handleResult, Long monitorId, Date cdate) {
        this.id = id;
        this.uid = uid;
        this.handleResult = handleResult;
        this.monitorId = monitorId;
        this.cdate = cdate;
    }

    public UserHandleHistoryResult() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    @Override
    public String toString() {
        return "UserHandleHistoryResult{" +
                "id=" + id +
                ", uid=" + uid +
                ", handleResult='" + handleResult + '\'' +
                ", monitorId=" + monitorId +
                ", cdate=" + cdate +
                '}';
    }
}
