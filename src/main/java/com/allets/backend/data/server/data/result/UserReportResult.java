package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;


@ApiObject(name = "UserBlackResult", description = "user report result")
public class UserReportResult {

    @ApiObjectField(description = "uid")
    Long uid;
    @ApiObjectField(description = "latestReportTime")
    Date latestReportTime;
    @ApiObjectField(description = "reportedCount")
    Integer reportedCount;
    @ApiObjectField(description = "deletedCommentCount")
    Integer deletedCommentCount;
    @ApiObjectField(description = "name")
    String name;
    @ApiObjectField(description = "reportType")
    String reportType;
    @ApiObjectField(description = "followCount")
    Integer followCount;
    @ApiObjectField(description = "reportTypeMessage")
    String reportTypeMessage;

    public Integer getDeletedCommentCount() {
        return deletedCommentCount;
    }

    public void setDeletedCommentCount(Integer deletedCommentCount) {
        this.deletedCommentCount = deletedCommentCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Date getLatestReportTime() {
        return latestReportTime;
    }

    public void setLatestReportTime(Date latestReportTime) {
        this.latestReportTime = latestReportTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(Integer reportedCount) {
        this.reportedCount = reportedCount;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportTypeMessage() {
        return reportTypeMessage;
    }

    public void setReportTypeMessage(String reportTypeMessage) {
        this.reportTypeMessage = reportTypeMessage;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserReportResult{");
        sb.append("deletedCommentCount=").append(deletedCommentCount);
        sb.append(", latestReportTime=").append(latestReportTime);
        sb.append(", reportedCount=").append(reportedCount);
        sb.append(", name='").append(name).append('\'');
        sb.append(", reportType='").append(reportType).append('\'');
        sb.append(", followCount=").append(followCount);
        sb.append(", uid=").append(uid);
        sb.append(", reportTypeMessage='").append(reportTypeMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
