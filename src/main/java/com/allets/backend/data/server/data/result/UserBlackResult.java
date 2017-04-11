package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by jack on 2015/9/10.
 */
@ApiObject(name = "UserBlackResult", description = "user black result for black list")
@Entity
public class UserBlackResult {

    @ApiObjectField(description = "id")
    private Integer id;
    @ApiObjectField(description = "nickName")
    private String nickName;
    @ApiObjectField(description = "cdate")
    private Date cdate;
    @ApiObjectField(description = "reportedCount")
    private Integer reportedCount;
    @ApiObjectField(description = "commentDeleteCount")
    private Integer commentDeleteCount;
    @ApiObjectField(description = "followerCount")
    private Integer followerCount;
    @ApiObjectField(description = "email")
    private String email;
    @ApiObjectField(description = "uid")
    private Integer uid;
    @ApiObjectField(description = "monitorId")
    private Integer monitorId;
    @ApiObjectField(description = "monitorName")
    private String monitorName;

    public UserBlackResult() {
    }

    public UserBlackResult(Integer id, String nickName, Date cdate, Integer reportedCount, Integer commentDeleteCount, Integer followerCount, String email, Integer uid, Integer monitorId, String monitorName) {
        this.id = id;
        this.nickName = nickName;
        this.cdate = cdate;
        this.reportedCount = reportedCount;
        this.commentDeleteCount = commentDeleteCount;
        this.followerCount = followerCount;
        this.email = email;
        this.uid = uid;
        this.monitorId = monitorId;
        this.monitorName = monitorName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Integer getReportedCount() {
        return reportedCount;
    }

    public void setReportedCount(Integer reportedCount) {
        this.reportedCount = reportedCount;
    }

    public Integer getCommentDeleteCount() {
        return commentDeleteCount;
    }

    public void setCommentDeleteCount(Integer commentDeleteCount) {
        this.commentDeleteCount = commentDeleteCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserBlackResult{");
        sb.append("id=").append(id);
        sb.append(", nickName='").append(nickName).append('\'');
        sb.append(", cdate=").append(cdate);
        sb.append(", reportedCount=").append(reportedCount);
        sb.append(", commentDeleteCount=").append(commentDeleteCount);
        sb.append(", followerCount=").append(followerCount);
        sb.append(", email='").append(email).append('\'');
        sb.append(", uid=").append(uid);
        sb.append(", monitorId=").append(monitorId);
        sb.append(", monitorName='").append(monitorName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
