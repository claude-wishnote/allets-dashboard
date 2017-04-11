package com.allets.backend.data.server.data.dto;

import com.allets.backend.data.server.entity.common.User;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;

/**
 * user general information
 */
@ApiObject(name = "UserDTO", description = "User simple info(should be replaced by userResult)")
public class UserDTO {

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
    private int bookMarkCount;
    @ApiObjectField(description = "subscribeEditorCount")
    private int subscribeEditorCount;
    @ApiObjectField(description = "subscribeCount")
    private int subscribeCount;
    @ApiObjectField(description = "subscriberCount")
    private int subscriberCount;
    @ApiObjectField(description = "deleteCommentsCount")
    private int deleteCommentsCount;
//    private int totalUserReported;

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

    @ApiObjectField(description = "shareCount")
    private Integer shareCount;
    @ApiObjectField(description = "alertCount")
    private Integer alertCount;

    public UserDTO() {
    }

    public UserDTO(User u) {
        this.uid = u.getUid();
        this.name = u.getName();
        this.sex = u.getSex();
        this.birthday = u.getBirthday();
        this.cdate = u.getCdate();
        this.email = u.getEmail();
        this.photo = u.getPhoto();
        this.level = u.getLevel();
        this.status = u.getStatus();
    }

    public UserDTO(Long uid, String name, String sex, String birthday, Date cdate, String email, String photo, String level, int bookMarkCount, int subscribeEditorCount, int subscribeCount, int subscriberCount, int deleteCommentsCount, String introMessage, String status, Date invalidFrom, Date invalidTo, String previousStatus, boolean blackList, Integer shareCount, Integer alertCount) {
        this.uid = uid;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.cdate = cdate;
        this.email = email;
        this.photo = photo;
        this.level = level;
        this.bookMarkCount = bookMarkCount;
        this.subscribeEditorCount = subscribeEditorCount;
        this.subscribeCount = subscribeCount;
        this.subscriberCount = subscriberCount;
        this.deleteCommentsCount = deleteCommentsCount;
        this.introMessage = introMessage;
        this.status = status;
        this.invalidFrom = invalidFrom;
        this.invalidTo = invalidTo;
        this.previousStatus = previousStatus;
        this.blackList = blackList;
        this.shareCount = shareCount;
        this.alertCount = alertCount;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroMessage() {
        return introMessage;
    }

    public void setIntroMessage(String introMessage) {
        this.introMessage = introMessage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public int getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(int subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", cdate=" + cdate +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", level='" + level + '\'' +
                ", bookMarkCount=" + bookMarkCount +
                ", subscribeEditorCount=" + subscribeEditorCount +
                ", subscribeCount=" + subscribeCount +
                ", subscriberCount=" + subscriberCount +
                ", deleteCommentsCount=" + deleteCommentsCount +
                ", introMessage='" + introMessage + '\'' +
                ", status='" + status + '\'' +
                ", invalidFrom=" + invalidFrom +
                ", invalidTo=" + invalidTo +
                ", previousStatus='" + previousStatus + '\'' +
                ", blackList=" + blackList +
                ", shareCount=" + shareCount +
                ", alertCount=" + alertCount +
                '}';
    }

    public int getBookMarkCount() {
        return bookMarkCount;
    }

    public void setBookMarkCount(int bookMarkCount) {
        this.bookMarkCount = bookMarkCount;
    }

    public int getSubscribeEditorCount() {
        return subscribeEditorCount;
    }

    public void setSubscribeEditorCount(int subscribeEditorCount) {
        this.subscribeEditorCount = subscribeEditorCount;
    }

    public int getDeleteCommentsCount() {
        return deleteCommentsCount;
    }

    public void setDeleteCommentsCount(int deleteCommentsCount) {
        this.deleteCommentsCount = deleteCommentsCount;
    }

    public int getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(int subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public boolean isBlackList() {
        return blackList;
    }

    public void setBlackList(boolean blackList) {
        this.blackList = blackList;
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
}
