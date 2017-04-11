package com.allets.backend.data.server.entity.common;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by claude on 2015/8/28.
 */
@Entity
@Table(name = "COMMENT_HANDLE_HISTORY", catalog = "allets_common")
public class CommentHandleHistory  implements java.io.Serializable {
    private static final long serialVersionUID = 7645212279514435433L;
    private long id;
    private long commentId;
    private String handleResult;
    private long monitorId;
    private Date cdate;

    public CommentHandleHistory() {
    }

    public CommentHandleHistory(long id, long commentId, String handleResult, long monitorId, Date cdate) {
        this.id = id;
        this.commentId = commentId;
        this.handleResult = handleResult;
        this.monitorId = monitorId;
        this.cdate = cdate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "comment_id", nullable = false, insertable = true, updatable = true)
    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
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
    @Temporal(TemporalType.TIMESTAMP)
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

        CommentHandleHistory that = (CommentHandleHistory) o;

        if (id != that.id) return false;
        if (commentId != that.commentId) return false;
        if (monitorId != that.monitorId) return false;
        if (handleResult != null ? !handleResult.equals(that.handleResult) : that.handleResult != null) return false;
        if (cdate != null ? !cdate.equals(that.cdate) : that.cdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (commentId ^ (commentId >>> 32));
        result = 31 * result + (handleResult != null ? handleResult.hashCode() : 0);
        result = 31 * result + (int) (monitorId ^ (monitorId >>> 32));
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        return result;
    }
}
