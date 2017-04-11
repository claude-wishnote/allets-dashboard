package com.allets.backend.data.server.entity.common;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by claude on 2015/9/8.
 */
@Entity
@Table(name = "USER_REPORT", schema = "", catalog = "allets_common")
public class UserReport {
    private long id;
    private long reportedUid;
    private long uid;
    private String reportType;
    private String reportDescription;
    private Date cdate;
    private String handleResult;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reported_uid", nullable = false, insertable = true, updatable = true)
    public long getReportedUid() {
        return reportedUid;
    }

    public void setReportedUid(long reportedUid) {
        this.reportedUid = reportedUid;
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
    @Column(name = "report_type", nullable = false, insertable = true, updatable = true, length = 5)
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @Basic
    @Column(name = "report_description", nullable = true, insertable = true, updatable = true, length = 500)
    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    @Basic
    @Column(name = "cdate", nullable = false, insertable = true, updatable = true)
    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    @Basic
    @Column(name = "handle_result", nullable = true, insertable = true, updatable = true, length = 4)
    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserReport that = (UserReport) o;

        if (id != that.id) return false;
        if (reportedUid != that.reportedUid) return false;
        if (uid != that.uid) return false;
        if (reportType != null ? !reportType.equals(that.reportType) : that.reportType != null) return false;
        if (reportDescription != null ? !reportDescription.equals(that.reportDescription) : that.reportDescription != null)
            return false;
        if (cdate != null ? !cdate.equals(that.cdate) : that.cdate != null) return false;
        if (handleResult != null ? !handleResult.equals(that.handleResult) : that.handleResult != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (reportedUid ^ (reportedUid >>> 32));
        result = 31 * result + (int) (uid ^ (uid >>> 32));
        result = 31 * result + (reportType != null ? reportType.hashCode() : 0);
        result = 31 * result + (reportDescription != null ? reportDescription.hashCode() : 0);
        result = 31 * result + (cdate != null ? cdate.hashCode() : 0);
        result = 31 * result + (handleResult != null ? handleResult.hashCode() : 0);
        return result;
    }
}
