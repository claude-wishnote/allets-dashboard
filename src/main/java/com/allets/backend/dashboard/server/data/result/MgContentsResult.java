package com.allets.backend.dashboard.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.Date;

/**
 * Created by claude on 2016/1/24.
 */
@ApiObject(name = "MgContentsResult", description = "MgContents Result")
public class MgContentsResult {

    @ApiObjectField(description = "contentsId")
    Long  contentsId;

    @ApiObjectField(description = "title")
    String title;

    @ApiObjectField(description = "contentsType")
    String  contentsType;


    Date udate;
    String editorName;
    Integer uid;
    Integer webViewCount;
    Integer appViewCount;
    Integer likeCount;
    Integer shareCount;
    Integer bookmarkCount;
    Integer commentCount;

    public MgContentsResult() {
        super();
    }

    public MgContentsResult(Long contentsId, String title, String contentsType) {
        this.contentsId = contentsId;
        this.title = title;
        this.contentsType = contentsType;
    }

    public Long getContentsId() {
        return contentsId;
    }

    public void setContentsId(Long contentsId) {
        this.contentsId = contentsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentsType() {
        return contentsType;
    }

    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getWebViewCount() {
        return webViewCount;
    }

    public void setWebViewCount(Integer webViewCount) {
        this.webViewCount = webViewCount;
    }

    public Integer getAppViewCount() {
        return appViewCount;
    }

    public void setAppViewCount(Integer appViewCount) {
        this.appViewCount = appViewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "MgContentsResult{" +
                "contentsId=" + contentsId +
                ", title='" + title + '\'' +
                ", contentsType='" + contentsType + '\'' +
                ", udate=" + udate +
                ", editorName='" + editorName + '\'' +
                ", uid=" + uid +
                ", webViewCount=" + webViewCount +
                ", appViewCount=" + appViewCount +
                ", likeCount=" + likeCount +
                ", shareCount=" + shareCount +
                ", bookmarkCount=" + bookmarkCount +
                ", commentCount=" + commentCount +
                '}';
    }
}
