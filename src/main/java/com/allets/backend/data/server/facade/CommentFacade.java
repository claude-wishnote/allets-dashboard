package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.data.result.CommentResult;
import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import org.springframework.data.domain.Page;

/**
 * Created by claude on 2015/8/25.
 */
public interface CommentFacade {

    Page<CommentResult> findCommentsList(String action, Integer offset, Integer limit, String q) throws Exception;

    String modifyReportedComment(String action,String commentIds, Long monitorId) throws Exception;

    Page<CommentResult> findHandledCommentList(Integer offset, Integer limit,String q) throws Exception;

    ReportTypeCountResult findReportTypeCountResult(Long uid) throws Exception;

    Integer findBestCommentCount(Long uid) throws Exception;

    Page<CommentResult> findAllCommentByUid(Integer offset, Integer limit,String q) throws Exception;

    String reportAndHandleComment(String reportType, String status, String commentIds, Long monitorId) throws Exception;

    CommentResult findCommentsContextByCommentId(Long commentId,String contentId,String cardId,String parentCommentId);
  }
