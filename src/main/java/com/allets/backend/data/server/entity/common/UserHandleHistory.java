package com.allets.backend.data.server.entity.common;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jack on 2015/9/6.
 */
@Entity
@Table(name = "USER_HANDLE_HISTORY", schema = "", catalog = "allets_common")
public class UserHandleHistory {
    private long id;
    private long uid;
    private String handleResult;
    private long monitorId;
    private Date cdate;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uid", nullable = false, insertable = true, updatable = true)
    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "handle_result", nullable = false, insertable = true, updatable = true, length = 4)
    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    @Basic
    @Column(name = "monitor_id", nullable = false, insertable = true, updatable = true)
    public long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(long monitorId) {
        this.monitorId = monitorId;
    }

    @Basic
    @Column(name = "cdate", nullable = false, insertable = true, updatable = true)
    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserHandleHistory that = (UserHandleHistory) o;

        if (id != that.id) return false;
        if (uid != that.uid) return false;
        if (monitorId != that.monitorId) return false;
        if (handleResult != null ? !handleResult.equals(that.handleResult) : that.handleResult != null) return false;
        if (cdate != null ? !cdate.equals(that.cdate) : that.cdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + (handleResult != null ? handleResult.hashCode() : 0);
        result = 31 * result + (int) (monitorId ^ (monitorId >>> 32));
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserHandleHistory{" +
                "id=" + id +
                ", uid=" + uid +
                ", handleResult='" + handleResult + '\'' +
                ", monitorId=" + monitorId +
                ", cdate=" + cdate +
                '}';
    }
}
