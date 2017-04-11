package com.allets.backend.data.server.entity.common;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 9/6/15.
 */
@Entity
@Table(name = "USER_INVALID", schema = "", catalog = "allets_common")
public class UserInvalid {
    private long uid;
    private Date invaildFrom;
    private Date invaildTo;
    private String previousStatus;

    @Id
    @Column(name = "uid", nullable = false, insertable = true, updatable = true)
    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "invaild_from", nullable = false, insertable = true, updatable = true)
    public Date getInvaildFrom() {
        return invaildFrom;
    }

    public void setInvaildFrom(Date invaildFrom) {
        this.invaildFrom = invaildFrom;
    }

    @Basic
    @Column(name = "invaild_to", nullable = true, insertable = true, updatable = true)
    public Date getInvaildTo() {
        return invaildTo;
    }

    public void setInvaildTo(Date invaildTo) {
        this.invaildTo = invaildTo;
    }

    @Basic
    @Column(name = "previous_status", nullable = true, insertable = true, updatable = true)
    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInvalid that = (UserInvalid) o;

        if (uid != that.uid) return false;
        if (invaildFrom != null ? !invaildFrom.equals(that.invaildFrom) : that.invaildFrom != null) return false;
        if (invaildTo != null ? !invaildTo.equals(that.invaildTo) : that.invaildTo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (uid ^ (uid >>> 32));
        result = 31 * result + (invaildFrom != null ? invaildFrom.hashCode() : 0);
        result = 31 * result + (invaildTo != null ? invaildTo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserInvalid{" +
                "uid=" + uid +
                ", invaildFrom=" + invaildFrom +
                ", invaildTo=" + invaildTo +
                '}';
    }
}
