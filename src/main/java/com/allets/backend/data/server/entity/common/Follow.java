package com.allets.backend.data.server.entity.common;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by claude on 2015/9/13.
 */
@Entity
@Table(name = "FOLLOW", schema = "", catalog = "allets_common")
public class Follow {
    private long fid;
    private Long followUid;
    private Long followerUid;
    private Date cdate;

    @Id
    @Column(name = "fid", nullable = false, insertable = true, updatable = true)
    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    @Basic
    @Column(name = "follow_uid", nullable = true, insertable = true, updatable = true)
    public Long getFollowUid() {
        return followUid;
    }

    public void setFollowUid(Long followUid) {
        this.followUid = followUid;
    }

    @Basic
    @Column(name = "follower_uid", nullable = true, insertable = true, updatable = true)
    public Long getFollowerUid() {
        return followerUid;
    }

    public void setFollowerUid(Long followerUid) {
        this.followerUid = followerUid;
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

        Follow follow = (Follow) o;

        if (fid != follow.fid) return false;
        if (followUid != null ? !followUid.equals(follow.followUid) : follow.followUid != null) return false;
        if (followerUid != null ? !followerUid.equals(follow.followerUid) : follow.followerUid != null) return false;
        if (cdate != null ? !cdate.equals(follow.cdate) : follow.cdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (fid ^ (fid >>> 32));
        result = 31 * result + (followUid != null ? followUid.hashCode() : 0);
        result = 31 * result + (followerUid != null ? followerUid.hashCode() : 0);
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        return result;
    }
}
