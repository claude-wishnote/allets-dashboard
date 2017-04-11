package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import com.allets.backend.data.server.elasticsearch.ElasticSearchTemplate;
import com.allets.backend.data.server.repository.common.CommentReportRepository;
import com.allets.backend.data.server.repository.common.MonitorRepository;
import com.allets.backend.data.server.utils.ArrayUtil;
import com.allets.backend.data.server.data.result.CommentResult;
import com.allets.backend.data.server.elasticsearch.model.MG_COMMENT;
import com.allets.backend.data.server.entity.common.CommentReport;
import com.allets.backend.data.server.repository.common.CommentRepository;
import com.allets.backend.data.server.entity.common.CommentHandleHistory;
import com.allets.backend.data.server.entity.common.MgComment;
import com.allets.backend.data.server.repository.common.CommentHandleHistoryRepository;
import com.allets.backend.data.server.service.CommentService;
import com.allets.backend.data.server.consts.Const;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by claude on 2015/8/22.
 */
@Service
public class CommentServiceImpl implements CommentService {

    final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    MockDataProvider mockDataProvider;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentReportRepository commentReportRepository;

    @Autowired
    CommentHandleHistoryRepository commentHandleHistoryRepository;

    @Autowired
    MonitorRepository monitorRepository;

    @Autowired
    ElasticSearchTemplate elasticSearchTemplate;

    @Override
    public Page<CommentResult> selectReportedCommentsList(int offset, int limit, String q) throws Exception {
        Page<CommentResult> page = commentRepository.findAllReportedComment(offset, limit, q);
//        for (CommentResult commentResult : page.getContent()) {
//            commentResult.setLikeCount(commentRepository.findLikeCountByCommentId(commentResult.getCommentId()));
//            commentResult.setRecommentCount(commentRepository.findRecommentCountByCommentId(commentResult.getCommentId()));
//            commentResult.setReportType(commentRepository.findAllReportType(commentResult.getCommentId()));
//        }
        return page;
    }


    @Override
    public Page<CommentResult> selectAllCommenstList(int offset, int limit, String q) throws Exception {
        Page<CommentResult> page = commentRepository.findAllComments(offset, limit, q);
//        if (page != null) {
//            for (CommentResult commentResult : page.getContent()) {
//                if (!"ACTV".equals(commentResult.getStatus()) && commentResult.getMonitorId() != null) {
//                    String name = monitorRepository.findByMonitorId(commentResult.getMonitorId()).getName();
//                    commentResult.setMonitorName(name == null ? "-" : name);
//                }
//            }
//        }
        return page;
    }


    @Override
    public String updateReportedCommentsList(String status, String commentIdStrings, Long monitorId) throws Exception {
        String commentIds = commentRepository.clearList(commentIdStrings);
        if (commentIds == null || commentIds.equals("")) {
            if (Const.Status.ACTV.equals(status)) {
                commentRepository.updateMgCommentStatus(ArrayUtil.arrayUnique(commentIdStrings), status);
            }
            return "success";
        }
        String[] clearStrings = ArrayUtil.arrayUnique(commentIds);
        List<Long> list = new ArrayList<Long>();
        List<CommentHandleHistory> chhList = new ArrayList<CommentHandleHistory>();
        List<MG_COMMENT> MG_COMMENTList = new ArrayList<MG_COMMENT>();

        for (String string : clearStrings) {
            if (StringUtils.isNotBlank(string)) {
                if (!Const.Status.ACTV.equals(status)) {
                    CommentHandleHistory commentHandleHistory = new CommentHandleHistory();
                    commentHandleHistory.setHandleResult(status);
                    commentHandleHistory.setCommentId(Long.valueOf(string));
                    commentHandleHistory.setMonitorId(monitorId);
                    commentHandleHistory.setCdate(new Date());
                    chhList.add(commentHandleHistory);
                }
                MG_COMMENT mg_comment = new MG_COMMENT();
                mg_comment.setComment_id(Long.valueOf(string));
                mg_comment.setHandle_result(status);
                MG_COMMENTList.add(mg_comment);

                list.add(mg_comment.getComment_id());
            }
        }
        if (!Const.Status.ACTV.equals(status)) {
            commentHandleHistoryRepository.save(chhList);
        }
        commentRepository.updateMgCommentStatus(clearStrings, status);
        if (elasticSearchTemplate.getElasticsearchSwitch()) {
            elasticSearchTemplate.ModifyMgComment("allets_common", "MG_COMMENT", MG_COMMENTList);
            System.err.println("DeleteCommentReport");
            if (!Const.Status.ACTV.equals(status)) {
                elasticSearchTemplate.DeleteCommentReport("allets_common",
                        "COMMENT_REPORT", list);
            }
        }

        return "success";
    }

    @Override
    public Page<CommentResult> selectAllHandledComment(Integer offset, Integer limit, String q) throws Exception {
        return commentHandleHistoryRepository.findAllHandledComment(offset, limit, q);
    }

    @Override
    public ReportTypeCountResult selectReportTypeCountResult(Long uid) throws Exception {
        return commentRepository.findReportTypeCount(uid);
    }

    @Override
    public Integer selectBestCommentCount(Long uid) throws Exception {
        return commentRepository.findBestCommentCount(uid);
    }

    @Override
    public Page<CommentResult> selectAllCommentByUid(int offset, int limit, String q) throws Exception {
        Page<CommentResult> list = commentRepository.findAllCommentByUid(offset, limit, q);
//        if (list != null && list.getSize() > 0) {
//            for (CommentResult commentResult : list) {
//                if (commentResult.getMonitorId() != null && StringUtils.isNotBlank(commentResult.getMonitorId().toString())) {
//                    commentResult.setMonitorName(monitorRepository.findByMonitorId(commentResult.getMonitorId()).getName());
//                }
//            }
//        }
        return list;
    }

    @Override
    public String insertAndUpdateCommentReportHandle(String reportType, String status, String commentIdStrings, Long monitorId) throws Exception {
        String commentIds = commentRepository.clearList(commentIdStrings);
        if (commentIds == null || commentIds.equals("")) {
            return "success";
        }
        String[] clearStrings = ArrayUtil.arrayUnique(commentIds);
        List<Long> list = new ArrayList<Long>();
        List<CommentReport> crList = new ArrayList<CommentReport>();
        List<CommentHandleHistory> chhList = new ArrayList<CommentHandleHistory>();
        List<MG_COMMENT> MG_COMMENTList = new ArrayList<MG_COMMENT>();
        for (String string : clearStrings) {
            if (StringUtils.isNotBlank(string)) {
                CommentReport commentReport = new CommentReport();
                //use uid 1 as defult
                commentReport.setUid(1);
                commentReport.setReportType(reportType);
                commentReport.setCommentId(Long.valueOf(string));
                commentReport.setCdate(new Date());
                commentReport.setHandleResult(status);
                crList.add(commentReport);

                CommentHandleHistory commentHandleHistory = new CommentHandleHistory();
                commentHandleHistory.setHandleResult(status);
                commentHandleHistory.setCommentId(Long.valueOf(string));
                commentHandleHistory.setMonitorId(monitorId);
                commentHandleHistory.setCdate(new Date());
                chhList.add(commentHandleHistory);

                MG_COMMENT mg_comment = new MG_COMMENT();
                mg_comment.setComment_id(Long.valueOf(string));
                mg_comment.setHandle_result(status);
                MG_COMMENTList.add(mg_comment);

                list.add(mg_comment.getComment_id());
            }
        }
        if (!Const.Status.PASS.equals(status)) {
            commentReportRepository.save(crList);
        }
        commentHandleHistoryRepository.save(chhList);
        commentRepository.updateMgCommentStatusForAllComments(clearStrings, status);
        if (elasticSearchTemplate.getElasticsearchSwitch()) {
            elasticSearchTemplate.ModifyMgComment("allets_common", "MG_COMMENT", MG_COMMENTList);
            if (!Const.Status.ACTV.equals(status)) {
                elasticSearchTemplate.DeleteCommentReport("allets_common",
                        "COMMENT_REPORT", list);
            }
        }
        return "success";
    }

    @Override
    public CommentResult selectReplyCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId, Long parentCommentId) {
        CommentResult commentResult = commentRepository.findReplyCardCommentsContextByCommentId(commentId, contentId, cardId, parentCommentId);
        MgComment parentComment = commentRepository.findByCommentId(parentCommentId);
        if (parentComment != null) {
            commentResult.setCommentId(parentCommentId);
            commentResult.setStatus(parentComment.getStatus());
            commentResult.setCommentText(parentComment.getText());
        }
        return commentResult;
    }

    @Override
    public CommentResult selectReplyContentCommentsContextByCommentId(Long commentId, Long contentId, Long parentCommentId) {
        CommentResult commentResult = commentRepository.findReplyContentCommentsContextByCommentId(commentId, contentId, parentCommentId);
        MgComment parentComment = commentRepository.findByCommentId(parentCommentId);
        if (parentComment != null) {
            commentResult.setCommentId(parentCommentId);
            commentResult.setStatus(parentComment.getStatus());
            commentResult.setCommentText(parentComment.getText());
        }
        return commentResult;
    }

    @Override
    public CommentResult selectCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId) {
        return commentRepository.findCardCommentsContextByCommentId(commentId, contentId, cardId);
    }

    @Override
    public CommentResult selectContentCommentsContextByCommentId(Long commentId, Long contentId) {
        return commentRepository.findContentCommentsContextByCommentId(commentId, contentId);
    }
}
