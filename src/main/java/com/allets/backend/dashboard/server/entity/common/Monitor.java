package com.allets.backend.dashboard.server.entity.common;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by claude on 2016/2/1.
 */
@Entity
@Table(name="MONITOR"
        ,catalog="allets_common"
)
public class Monitor {
    private long monitorId;
    private String name;
    private String password;
    private String oldPassword;
    private String level;
    private String status;
    private Date udate;
    private Date cdate;
    private Date lastLoginTime;

    @Id
    @Column(name = "monitor_id", nullable = false, insertable = true, updatable = true)
    public long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(long monitorId) {
        this.monitorId = monitorId;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 256)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "level", nullable = false, insertable = true, updatable = true, length = 20)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Basic
    @Column(name = "status", nullable = false, insertable = true, updatable = true, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "udate", nullable = true, insertable = true, updatable = true)
    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    @Basic
    @Column(name = "cdate", nullable = false, insertable = true, updatable = true)
    public Date getCdate() {
        return cdate;
    }

    @Transient
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    @Basic
    @Column(name = "last_login_time", nullable = true, insertable = true, updatable = true)
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Monitor monitor = (Monitor) o;

        if (monitorId != monitor.monitorId) return false;
        if (name != null ? !name.equals(monitor.name) : monitor.name != null) return false;
        if (password != null ? !password.equals(monitor.password) : monitor.password != null) return false;
        if (level != null ? !level.equals(monitor.level) : monitor.level != null) return false;
        if (status != null ? !status.equals(monitor.status) : monitor.status != null) return false;
        if (udate != null ? !udate.equals(monitor.udate) : monitor.udate != null) return false;
        if (cdate != null ? !cdate.equals(monitor.cdate) : monitor.cdate != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(monitor.lastLoginTime) : monitor.lastLoginTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (monitorId ^ (monitorId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (udate != null ? udate.hashCode() : 0);
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        return result;
    }
}
