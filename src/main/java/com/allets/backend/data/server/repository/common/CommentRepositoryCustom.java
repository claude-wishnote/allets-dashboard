package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.CommentResult;
import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import org.springframework.data.domain.Page;

/**
 * Created by claude on 2015/8/24.
 */
public interface CommentRepositoryCustom {

    Page<CommentResult> findAllReportedComment(Integer offset, Integer limit,String q) throws Exception;

    Integer findLikeCountByCommentId(Long commentId) throws Exception;

    Integer findRecommentCountByCommentId(Long commentId) throws Exception;

    String findSlangType(String commentText) throws Exception;

    String findAllReportType(Long commentId) throws Exception;

    void updateMgCommentStatus(Long commentId, String status) throws Exception;

    void updateMgCommentStatus(String[] commentIds, String status) throws Exception;

    ReportTypeCountResult findReportTypeCount(Long uid) throws Exception;

    Integer findBestCommentCount(Long uid) throws Exception;

    Page<CommentResult> findAllCommentByUid(Integer offset, Integer limit, String q) throws Exception;

    Page<CommentResult> findAllComments(Integer offset, Integer limit, String q) throws Exception;

    void updateMgCommentStatusForAllComments(String[] commentIds, String status) throws Exception;

    CommentResult findReplyCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId, Long parentCommentId);

    CommentResult findReplyContentCommentsContextByCommentId(Long commentId, Long contentId, Long parentCommentId);

    CommentResult findCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId);

    CommentResult findContentCommentsContextByCommentId(Long commentId, Long contentId);

    String clearList(String commentIdStrings) throws Exception;
}
