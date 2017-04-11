package com.allets.backend.data.server.service;


import com.allets.backend.data.server.data.result.CommentResult;
import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by claude on 2015/8/22.
 */
@Service
public interface CommentService {

    public Page<CommentResult> selectReportedCommentsList(int offset, int limit, String q) throws Exception;

    public Page<CommentResult> selectAllCommenstList(int offset, int limit, String q) throws Exception;

    public String updateReportedCommentsList(String status, String commentIds, Long monitorId) throws Exception;

    public Page<CommentResult> selectAllHandledComment(Integer offset, Integer limit,String q) throws Exception;

    public ReportTypeCountResult selectReportTypeCountResult(Long uid) throws Exception;

    public Integer selectBestCommentCount(Long uid) throws Exception;

    public Page<CommentResult> selectAllCommentByUid(int offset, int limit, String q) throws Exception;

    public String insertAndUpdateCommentReportHandle(String reportType, String status, String commentIds, Long monitorId) throws Exception;

    public CommentResult selectReplyCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId, Long parentCommentId);

    public CommentResult selectReplyContentCommentsContextByCommentId(Long commentId, Long contentId, Long parentCommentId);

    public CommentResult selectCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId);

    public CommentResult selectContentCommentsContextByCommentId(Long commentId, Long contentId);


}
