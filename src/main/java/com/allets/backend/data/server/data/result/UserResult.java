package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;

/**
 * Created by claude on 2015/9/11.
 */
@ApiObject(name = "UserResult", description = "user result")
public class UserResult {

    @ApiObjectField(description = "uid")
    private Long uid;
    @ApiObjectField(description = "name")
    private String name;
    @ApiObjectField(description = "sex")
    private String sex;
    @ApiObjectField(description = "birthday")
    private String birthday;
    @ApiObjectField(description = "cdate")
    private Date cdate;
    @ApiObjectField(description = "email")
    private String email;
    @ApiObjectField(description = "photo")
    private String photo;
    @ApiObjectField(description = "level")
    private String level;
    @ApiObjectField(description = "bookMarkCount")
    private Integer bookMarkCount;
    @ApiObjectField(description = "subscribeCount")
    private Integer subscribeCount;
    @ApiObjectField(description = "subscribeEditorCount")
    private Integer subscribeEditorCount;
    @ApiObjectField(description = "subscriberCount")
    private Integer subscriberCount;
    @ApiObjectField(description = "deleteCommentsCount")
    private Integer deleteCommentsCount;

    @ApiObjectField(description = "introMessage")
    private String introMessage;
    @ApiObjectField(description = "status")
    private String status;

    @ApiObjectField(description = "invalidFrom")
    private Date invalidFrom;
    @ApiObjectField(description = "invalidTo")
    private Date invalidTo;
    @ApiObjectField(description = "previousStatus")
    private String previousStatus;

    @ApiObjectField(description = "blackList")
    private boolean blackList;
    /**
     * report count reported by user
     */
    @ApiObjectField(description = "userReportedCount")
    private Integer userReportedCount;
    /**
     * total report count
     */
    @ApiObjectField(description = "reportedCount")
    private Integer reportedCount;
    @ApiObjectField(description = "shareCount")
    private Integer shareCount;
    @ApiObjectField(description = "alertCount")
    private Integer alertCount;

    @ApiObjectField(description = "latestReportTime")
    private Date latestReportTime;
    @ApiObjectField(description = "reportType")
    private String reportType;
    @ApiObjectField(description = "reportTypeMessage")
    private String reportTypeMessage;

    @ApiObjectField(description = "monitorName")
    private String monitorName;
    @ApiObjectField(description = "handleResult")
    private String handleResult;

    @ApiObjectField(description = "age15plus")
    private Integer age15plus;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getBookMarkCount() {
        return bookMarkCount;
    }

    public void setBookMarkCount(Integer bookMarkCount) {
        this.bookMarkCount = bookMarkCount;
    }

    public Integer getSubscribeEditorCount() {
        return subscribeEditorCount;
    }

    public void setSubscribeEditorCount(Integer subscribeEditorCount) {
        this.subscribeEditorCount = subscribeEditorCount;
    }

    public Integer getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(Integer subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public Integer getDeleteCommentsCount() {
        return deleteCommentsCount;
    }

    public void setDeleteCommentsCount(Integer deleteCommentsCount) {
        this.deleteCommentsCount = deleteCommentsCount;
    }

    public String getIntroMessage() {
        return introMessage;
    }

    public void setIntroMessage(String introMessage) {
        this.introMessage = introMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInvalidFrom() {
        return invalidFrom;
    }

    public void setInvalidFrom(Date invalidFrom) {
        this.invalidFrom = invalidFrom;
    }

    public Date getInvalidTo() {
        return invalidTo;
    }

    public void setInvalidTo(Date invalidTo) {
        this.invalidTo = invalidTo;
    }

    public boolean isBlackList() {
        return blackList;
    }

    public void setBlackList(boolean blackList) {
        this.blackList = blackList;
    }

    public Integer getUserReportedCount() {
        return userReportedCount;
    }

    public void setUserReportedCount(Integer userReportedCount) {
        this.userReportedCount = userReportedCount;
    }

    public Integer getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(Integer reportedCount) {
        this.reportedCount = reportedCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getAlertCount() {
        return alertCount;
    }

    public void setAlertCount(Integer alertCount) {
        this.alertCount = alertCount;
    }

    public Date getLatestReportTime() {
        return latestReportTime;
    }

    public void setLatestReportTime(Date latestReportTime) {
        this.latestReportTime = latestReportTime;
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

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Integer getAge15plus() {
        return age15plus;
    }

    public void setAge15plus(Integer age15plus) {
        this.age15plus = age15plus;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public Integer getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Integer subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", cdate=" + cdate +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", level='" + level + '\'' +
                ", bookMarkCount=" + bookMarkCount +
                ", subscribeCount=" + subscribeCount +
                ", subscribeEditorCount=" + subscribeEditorCount +
                ", subscriberCount=" + subscriberCount +
                ", deleteCommentsCount=" + deleteCommentsCount +
                ", introMessage='" + introMessage + '\'' +
                ", status='" + status + '\'' +
                ", invalidFrom=" + invalidFrom +
                ", invalidTo=" + invalidTo +
                ", previousStatus='" + previousStatus + '\'' +
                ", blackList=" + blackList +
                ", userReportedCount=" + userReportedCount +
                ", reportedCount=" + reportedCount +
                ", shareCount=" + shareCount +
                ", alertCount=" + alertCount +
                ", latestReportTime=" + latestReportTime +
                ", reportType='" + reportType + '\'' +
                ", reportTypeMessage='" + reportTypeMessage + '\'' +
                ", monitorName='" + monitorName + '\'' +
                ", handleResult='" + handleResult + '\'' +
                ", age15plus=" + age15plus +
                '}';
    }
}
