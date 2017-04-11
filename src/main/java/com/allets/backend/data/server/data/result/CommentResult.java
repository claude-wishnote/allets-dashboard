package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;
import java.util.List;

/**
 * Created by claude on 2015/8/24.
 */
@ApiObject(name = "CommentResult", description = "a comment result with all kinds of datas need to show in front end")
public class CommentResult {

    @ApiObjectField(description = "comment id")
    Long commentId;
    @ApiObjectField(description = "comment sender's uid")
    Long uid;
    @ApiObjectField(description = "comment sender's nickName")
    String nickName;
    @ApiObjectField(description = "comment content text")
    String commentText;
    @ApiObjectField(description = "comment create date")
    Date cDate;
    @ApiObjectField(description = "reported count of comment")
    Integer reportCount;
    @ApiObjectField(description = "replied count of comment")
    Integer recommentCount;
    @ApiObjectField(description = "comment or replied comment")
    String commentType;
    @ApiObjectField(description = "reported type of comment report")
    String reportType;
    @ApiObjectField(description = "reported Id of comment report")
    Long reportId;
    @ApiObjectField(description = "reported message of  comment report")
    String reportTypeMessage;
    @ApiObjectField(description = "like count of  comment")
    Integer likeCount;
    @ApiObjectField(description = "slang type of  comment")
    String slangType;
    @ApiObjectField(description = "report description of  comment report")
    String reportDescription;
    @ApiObjectField(description = "contentId")
    Long contentId;
    @ApiObjectField(description = "cardId")
    Long cardId;
    @ApiObjectField(description = "cardOrdering")
    int cardOrdering;
    @ApiObjectField(description = "monitor id of monitor who handle this comment report")
    Long monitorId;
    @ApiObjectField(description = "monitor name of monitor who handle this comment report")
    String monitorName;
    @ApiObjectField(description = "condition of comment report")
    String condition;
    @ApiObjectField(description = "handle result of comment report")
    String handleResult;
    @ApiObjectField(description = "comment status")
    String status;
    @ApiObjectField(description = "not handle report count of comment")
    Integer notHandleReportCount;
    @ApiObjectField(description = "reply comment list of comment")
    List<CommentResult> commentResultList;
    @ApiObjectField(description = "parent comment id")
    Long parentCommentId;

    @ApiObjectField(description = "content card type for content context")
    String contentCardType;
    @ApiObjectField(description = "content card image for content context")
    String contentCardImage;

    @ApiObjectField(description = "in-house reported count of comment")
    Integer inHouseReportCount;

    public String getReportTypeMessage() {
        return reportTypeMessage;
    }

    public void setReportTypeMessage(String reportTypeMessage) {
        this.reportTypeMessage = reportTypeMessage;
    }

    public String getContentCardType() {
        return contentCardType;
    }

    public void setContentCardType(String contentCardType) {
        this.contentCardType = contentCardType;
    }

    public String getContentCardImage() {
        return contentCardImage;
    }

    public void setContentCardImage(String contentCardImage) {
        this.contentCardImage = contentCardImage;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public List<CommentResult> getCommentResultList() {
        return commentResultList;
    }

    public void setCommentResultList(List<CommentResult> commentResultList) {
        this.commentResultList = commentResultList;
    }

    public int getCardOrdering() {
        return cardOrdering;
    }

    public void setCardOrdering(int cardOrdering) {
        this.cardOrdering = cardOrdering;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }


    public String getSlangType() {
        return slangType;
    }

    public void setSlangType(String slangType) {
        this.slangType = slangType;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getcDate() {
        return cDate;
    }

    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public Integer getRecommentCount() {
        return recommentCount;
    }

    public void setRecommentCount(Integer recommentCount) {
        this.recommentCount = recommentCount;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Long getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Long monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNotHandleReportCount() {
        return notHandleReportCount;
    }

    public void setNotHandleReportCount(Integer notHandleReportCount) {
        this.notHandleReportCount = notHandleReportCount;
    }

    public Integer getInHouseReportCount() {
        return inHouseReportCount;
    }

    public void setInHouseReportCount(Integer inHouseReportCount) {
        this.inHouseReportCount = inHouseReportCount;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    @Override
    public String toString() {
        return "CommentResult{" +
                "commentId=" + commentId +
                ", uid=" + uid +
                ", nickName='" + nickName + '\'' +
                ", commentText='" + commentText + '\'' +
                ", cDate=" + cDate +
                ", reportCount=" + reportCount +
                ", recommentCount=" + recommentCount +
                ", commentType='" + commentType + '\'' +
                ", reportType='" + reportType + '\'' +
                ", reportId=" + reportId +
                ", reportTypeMessage='" + reportTypeMessage + '\'' +
                ", likeCount=" + likeCount +
                ", slangType='" + slangType + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                ", contentId=" + contentId +
                ", cardId=" + cardId +
                ", cardOrdering=" + cardOrdering +
                ", monitorId=" + monitorId +
                ", monitorName='" + monitorName + '\'' +
                ", condition='" + condition + '\'' +
                ", handleResult='" + handleResult + '\'' +
                ", status='" + status + '\'' +
                ", notHandleReportCount=" + notHandleReportCount +
                ", commentResultList=" + commentResultList +
                ", parentCommentId=" + parentCommentId +
                ", contentCardType='" + contentCardType + '\'' +
                ", contentCardImage='" + contentCardImage + '\'' +
                ", inHouseReportCount=" + inHouseReportCount +
                '}';
    }
}
