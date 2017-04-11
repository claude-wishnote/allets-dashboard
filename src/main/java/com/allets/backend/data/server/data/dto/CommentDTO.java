package com.allets.backend.data.server.data.dto;

import com.allets.backend.data.server.entity.common.MgComment;
import com.allets.backend.data.server.entity.common.MgCommentLike;
import com.allets.backend.data.server.entity.common.User;
import com.allets.backend.data.server.entity.common.CommentReport;

/**
 * Created by claude on 2015/8/24.
 */
public class CommentDTO {

    MgComment mgComment;
    CommentReport commentReport;
    User user;
    MgCommentLike mgCommentLike;

    public MgComment getMgComment() {
        return mgComment;
    }

    public void setMgComment(MgComment mgComment) {
        this.mgComment = mgComment;
    }

    public CommentReport getCommentReport() {
        return commentReport;
    }

    public void setCommentReport(CommentReport commentReport) {
        this.commentReport = commentReport;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MgCommentLike getMgCommentLike() {
        return mgCommentLike;
    }

    public void setMgCommentLike(MgCommentLike mgCommentLike) {
        this.mgCommentLike = mgCommentLike;
    }


}
