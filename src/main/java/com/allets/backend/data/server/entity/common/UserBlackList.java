package com.allets.backend.data.server.entity.common;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jack on 2015/9/14.
 */
@Entity
@Table(name = "USER_BLACK_LIST", schema = "", catalog = "allets_common")
public class UserBlackList {
    private long id;
    private long uid;
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

        UserBlackList blackList = (UserBlackList) o;

        if (id != blackList.id) return false;
        if (uid != blackList.uid) return false;
        if (monitorId != blackList.monitorId) return false;
        if (cdate != null ? !cdate.equals(blackList.cdate) : blackList.cdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + (int) (monitorId ^ (monitorId >>> 32));
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        return result;
    }
}
