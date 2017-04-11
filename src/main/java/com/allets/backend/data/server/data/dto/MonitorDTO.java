package com.allets.backend.data.server.data.dto;

import com.allets.backend.data.server.entity.common.Monitor;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;


@ApiObject(name = "MonitorDTO", description = "Monitor model")
public class MonitorDTO {

    @ApiObjectField(description = "monitorId")
    private Long monitorId;
    @ApiObjectField(description = "name")
    private String name;
    @ApiObjectField(description = "level")
    private String level;
    @ApiObjectField(description = "status")
    private String status;
    @ApiObjectField(description = "udate")
    private Date udate;
    @ApiObjectField(description = "cdate")
    private Date cdate;

    public MonitorDTO() {

    }

    public MonitorDTO(Monitor monitor) {
        this.monitorId = monitor.getMonitorId();
        this.name = monitor.getName();
        this.level = monitor.getLevel();
        this.status = monitor.getStatus();
        this.udate = monitor.getUdate();
        this.cdate = monitor.getCdate();
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MonitorDTO{");
        sb.append("cdate=").append(cdate);
        sb.append(", monitorId=").append(monitorId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", udate=").append(udate);
        sb.append('}');
        return sb.toString();
    }
}
