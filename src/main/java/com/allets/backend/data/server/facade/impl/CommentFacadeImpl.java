package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.exception.NotSupportActionException;
import com.allets.backend.data.server.facade.CommentFacade;
import com.allets.backend.data.server.data.result.CommentResult;
import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import com.allets.backend.data.server.service.CommentService;
import com.allets.backend.data.server.consts.Const;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created by claude on 2015/8/25.
 */
@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class CommentFacadeImpl implements CommentFacade {

    @Autowired
    CommentService commentService;

    @Override
    public Page<CommentResult> findCommentsList(String action, Integer offset, Integer limit, String q) throws Exception {
        Page<CommentResult> page = new PageImpl<CommentResult>(new ArrayList<CommentResult>(), new PageRequest(0, 1), 0);
        if (Const.Action.REPORTED.equals(action)) {
            page = commentService.selectReportedCommentsList(offset, limit, q);
        }
        if (Const.Action.HANDLED.equals(action)) {
            page = commentService.selectAllHandledComment(offset, limit, q);
        }
        if (Const.Action.ALLSIMPLE.equals(action)) {
            page = commentService.selectAllCommentByUid(offset, limit, q);
        }
        if (Const.Action.ALL.equals(action)) {
            page = commentService.selectAllCommenstList(offset, limit, q);
        }
        return page;
    }

    @Override
    @Transactional(value = "commonTxManager")
    public String modifyReportedComment(String action, String commentIds, Long monitorId) throws Exception {
        if (Const.Action.PASS.equals(action)) {
            return commentService.updateReportedCommentsList(Const.Status.PASS, commentIds, monitorId);
        } else if (Const.Action.HIDD.equals(action)) {
            return commentService.updateReportedCommentsList(Const.Status.HIDD, commentIds, monitorId);
        } else if (Const.Action.DEL.equals(action)) {
            return commentService.updateReportedCommentsList(Const.Status.DEL, commentIds, monitorId);
        }  else if (Const.Action.RES.equals(action)) {
            return commentService.updateReportedCommentsList(Const.Status.ACTV, commentIds, monitorId);
        } else {
            throw new NotSupportActionException();
        }
    }

    @Override
    public Page<CommentResult> findHandledCommentList(Integer offset, Integer limit, String q) throws Exception {
        return commentService.selectAllHandledComment(offset, limit, q);
    }

    @Override
    public ReportTypeCountResult findReportTypeCountResult(Long uid) throws Exception {
        return commentService.selectReportTypeCountResult(uid);
    }

    @Override
    public Integer findBestCommentCount(Long uid) throws Exception {
        return commentService.selectBestCommentCount(uid);
    }

    @Override
    public Page<CommentResult> findAllCommentByUid(Integer offset, Integer limit, String q) throws Exception {
        return commentService.selectAllCommentByUid(offset, limit, q);
    }

    @Override
    @Transactional(value = "commonTxManager")
    public String reportAndHandleComment(String reportType, String status, String commentIds, Long monitorId) throws Exception {
        return commentService.insertAndUpdateCommentReportHandle(reportType, status, commentIds, monitorId);
    }

    @Override
    public CommentResult findCommentsContextByCommentId(Long commentId, String contentId, String cardId, String parentCommentId) {
        CommentResult commentResult = null;
        if (StringUtils.isNotBlank(parentCommentId)) {
            if (StringUtils.isNotBlank(cardId)) {
                commentResult = commentService.selectReplyCardCommentsContextByCommentId(commentId, Long.valueOf(contentId), Long.valueOf(cardId), Long.valueOf(parentCommentId));
            } else {
                commentResult = commentService.selectReplyContentCommentsContextByCommentId(commentId, Long.valueOf(contentId), Long.valueOf(parentCommentId));
            }
        } else {
            if (StringUtils.isNotBlank(cardId)) {
                commentResult = commentService.selectCardCommentsContextByCommentId(commentId, Long.valueOf(contentId), Long.valueOf(cardId));
            } else {
                commentResult = commentService.selectContentCommentsContextByCommentId(commentId, Long.valueOf(contentId));
            }
        }
        return commentResult;
    }
}
