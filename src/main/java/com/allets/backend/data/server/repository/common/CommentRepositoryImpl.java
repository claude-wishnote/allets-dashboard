package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import com.allets.backend.data.server.elasticsearch.ElasticSearchTemplate;
import com.allets.backend.data.server.entity.common.*;
import com.allets.backend.data.server.exception.BadRequestException;
import com.allets.backend.data.server.utils.ArrayUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.expr.BooleanExpression;
import com.allets.backend.data.server.data.result.CommentResult;
import com.allets.backend.data.server.consts.Const;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by claude on 2015/8/24.
 */
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final Logger log = LoggerFactory
            .getLogger(CommentRepositoryImpl.class);

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

    @Autowired
    ElasticSearchTemplate elasticSearchTemplate;

    public List<SlangWord> list;

    //    sql:
//    SELECT friends.id_friend, player.username, score.area, max(score.level), max(score.area)
//    FROM player
//    LEFT JOIN friends ON player.id = friends.id_friend
//    LEFT JOIN score ON player.id = score.id
//    WHERE friends.id_player =1
//    GROUP BY score.id
//
//    querydsl:
// query.from(player)
// .leftJoin(friends)
// .on(player.id.eq(friends.idFriend))
// .leftJoin(score)
// .on(player.id.eq(score.id))
// .where(friends.idPlayer.eq(id))
// .list(player.id,player.username,score.area.max(),score.level.max())

    @Override
    public Page<CommentResult> findAllReportedComment(Integer offset, Integer limit, String q) throws Exception {

        JPAQuery slangWordListQuery = new JPAQuery(entityManager);
        QSlangWord qSlangWord = QSlangWord.slangWord;
        slangWordListQuery.from(qSlangWord);
        list = slangWordListQuery.list(qSlangWord);

        HashMap<String, Object> parametersMap = new HashMap<>();
        if (StringUtils.isNotBlank(q)) {
            q = URLDecoder.decode(q, "utf-8");
            String[] conditions = q.split(",");
            if (conditions != null & conditions.length > 0) {
                for (String parameter : conditions) {
                    String[] values = parameter.split("=");
                    parametersMap.put(values[0], values[1]);
                }
            }
        }
        String listSql = "";
        Query listQuery = null;
        List objecArraytList = null;
        int count = 0;
        if (parametersMap.containsKey("searchSource") && ((String) parametersMap.get("searchSource")).equals("els") && elasticSearchTemplate.getElasticsearchSwitch()) {
            Date startDate = null;
            Date endDate = null;
            String field = "keyword";
            String value = "";
            if (parametersMap.containsKey("email")) {
                field = "email";
                value = (String) parametersMap.get("email");
            } else if (parametersMap.containsKey("nickName")) {
                field = "nickName";
                value = (String) parametersMap.get("nickName");
            } else if (parametersMap.containsKey("keyword")) {
                field = "keyword";
                value = (String) parametersMap.get("keyword");
            }
            SearchHits hits = elasticSearchTemplate.searchReportComment(
                    "allets_common",
                    "COMMENT_REPORT",
                    field,
                    value,
                    startDate,//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-11-24 00:00:00"),
                    endDate,//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-11-27 00:00:00"),
                    offset, limit);
            count = (int) hits.totalHits();
            log.info(String.valueOf(count));
            if (hits.getHits().length > 0) {
                listSql = " SELECT" +
                        "       cr.comment_id, " +
                        "       (SELECT count(*) from COMMENT_REPORT where  comment_id=cr.comment_id) count, " +
                        "       (SELECT CASE WHEN cl.parent_comment_id IS NULL THEN 'C' ELSE 'RC' END AS 'Comment Type' FROM MG_COMMENT cl WHERE cl.comment_id = cr.comment_id) 'Comment Type', " +
                        "       (SELECT cdate FROM COMMENT_REPORT WHERE id = cr.id) maxCdate, " +
                        "       u.uid, " +
                        "       u.name, " +
                        "       mc.text," +
                        "       (select group_concat(DISTINCT(report_type)) from COMMENT_REPORT where comment_id=cr.comment_id and report_type IS NOT NULL) report_type, " +
                        "       (SELECT report_description FROM COMMENT_REPORT WHERE id = cr.id ) report_description, " +
                        "       mc.contents_id, " +
                        "       mc.card_id, " +
                        "       mcd.ordering, " +
                        "       (SELECT COUNT(*) FROM MG_COMMENT_LIKE cl WHERE cl.comment_id = cr.comment_id) likeCount, " +
                        "       (SELECT COUNT(*) FROM MG_COMMENT cc WHERE cc.parent_comment_id = cr.comment_id AND cc.status = 'ACTV') childCommentCount, " +
                        "       u.email," +
                        "       mc.parent_comment_id,";
                listSql = listSql +
                        "       (SELECT count(x.id) FROM COMMENT_REPORT x LEFT JOIN USER u ON x.uid=u.uid WHERE x.comment_id=cr.comment_id ";
                for (int i = 0; i < Const.InHouseUsers.size(); i++) {
                    String email = Const.InHouseUsers.get(i);
                    if (i == 0) {
                        listSql = listSql + " AND (u.email='" + email + "' ";
                    } else if (i == Const.InHouseUsers.size() - 1) {
                        listSql = listSql + " OR u.email='" + email + "') ";
                    } else {
                        listSql = listSql + " OR u.email='" + email + "'";
                    }
                }
                listSql = listSql +
                        ") inHouseReportCount ," +
                        " cr.id " +
                        " FROM COMMENT_REPORT cr  " +
                        "       LEFT JOIN MG_COMMENT mc ON mc.comment_id = cr.comment_id " +
                        "       LEFT JOIN MG_CARD mcd ON mcd.card_id = mc.card_id " +
                        "       LEFT JOIN USER u ON u.uid = mc.uid " +
                        " WHERE 1 = 1 ";
                for (int i = 0; i < hits.getHits().length; i++) {
                    Long reportId = Long.valueOf(hits.getHits()[i].getSource().get("report_id").toString());
                    if (i == 0) {
                        listSql = listSql + " AND ";
                    } else {
                        listSql = listSql + " OR ";
                    }
                    listSql = listSql + " cr.id = " + reportId;
                }
                listSql = listSql + " ORDER BY id DESC ";

                listQuery = entityManager.createNativeQuery(listSql);
                objecArraytList = listQuery.getResultList();
            } else {
                objecArraytList = new ArrayList<>();
            }
        } else {
            count = 10000;
            if ((parametersMap.containsKey("email") && StringUtils.isNotBlank((String) parametersMap.get("email")))
                    || (parametersMap.containsKey("nickName") && StringUtils.isNotBlank((String) parametersMap.get("nickName")))
                    || (parametersMap.containsKey("uid") && StringUtils.isNotBlank((String) parametersMap.get("uid")))) {
                String countSql = "SELECT  " +
                        " COUNT(cr.comment_id) " +
                        " FROM (SELECT comment_id  FROM COMMENT_REPORT WHERE handle_result IS NULL GROUP BY comment_id) cr" +
                        " LEFT JOIN MG_COMMENT mc ON mc.comment_id = cr.comment_id" +
                        " LEFT JOIN USER u ON u.uid = mc.uid" +
                        " WHERE 1 = 1 ";
                if (parametersMap.containsKey("email")) {
                    countSql = countSql + " AND u.email = '" + parametersMap.get("email") + "' ";
                } else if (parametersMap.containsKey("nickName")) {
                    countSql = countSql + " AND u.name = '" + parametersMap.get("nickName") + "' ";
                } else if (parametersMap.containsKey("uid")) {
                    countSql = countSql + " AND u.uid = '" + parametersMap.get("uid") + "' ";
                }

                Query countQuery = entityManager.createNativeQuery(countSql);
                List objecCountList = countQuery.getResultList();
                if (objecCountList == null) {
                    throw new BadRequestException();
                }
                if (objecCountList.size() > 0) {
                    count = Integer.valueOf(objecCountList.get(0).toString());
                }
            }
            listSql = " SELECT" +
                    "       cr.comment_id, " +
                    "       cr.count, " +
                    "       (SELECT CASE WHEN cl.parent_comment_id IS NULL THEN 'C' ELSE 'RC' END AS 'Comment Type' FROM MG_COMMENT cl WHERE cl.comment_id = cr.comment_id) 'Comment Type', " +
                    "       (SELECT cdate FROM COMMENT_REPORT WHERE id = cr.latestId) maxCdate, " +
                    "       u.uid, " +
                    "       u.name, " +
                    "       mc.text," +
                    "       (select group_concat(DISTINCT(report_type)) from COMMENT_REPORT where comment_id=cr.comment_id and report_type IS NOT NULL) report_type, " +
                    "       (SELECT report_description FROM COMMENT_REPORT WHERE comment_id = cr.latestId ) report_description, " +
                    "       mc.contents_id, " +
                    "       mc.card_id, " +
                    "       mcd.ordering, " +
                    "       (SELECT COUNT(*) FROM MG_COMMENT_LIKE cl WHERE cl.comment_id = cr.comment_id) likeCount, " +
                    "       (SELECT COUNT(*) FROM MG_COMMENT cc WHERE cc.parent_comment_id = cr.comment_id AND cc.status = 'ACTV') childCommentCount, " +
                    "       u.email," +
                    "       mc.parent_comment_id,";
            listSql = listSql +
                    "       (SELECT count(x.id) FROM COMMENT_REPORT x LEFT JOIN USER u ON x.uid=u.uid WHERE x.comment_id=cr.comment_id ";
            for (int i = 0; i < Const.InHouseUsers.size(); i++) {
                String email = Const.InHouseUsers.get(i);
                if (i == 0) {
                    listSql = listSql + " AND (u.email='" + email + "' ";
                } else if (i == Const.InHouseUsers.size() - 1) {
                    listSql = listSql + " OR u.email='" + email + "') ";
                } else {
                    listSql = listSql + " OR u.email='" + email + "'";
                }
            }
            listSql = listSql +
                    ") inHouseReportCount ," +
                    " latestId " +
                    " FROM " +
                    "       (SELECT comment_id, MAX(id) latestId, COUNT(*) count " +
                    "        FROM COMMENT_REPORT WHERE handle_result IS NULL  ";
            if (parametersMap != null) {
                if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                    if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                        String startDate = (String) parametersMap.get("startDate");
                        String endDate = (String) parametersMap.get("endDate");
                        listSql = listSql +
                                " AND (cdate BETWEEN '" + startDate + "'" +
                                " AND  '" + endDate + "')";
                    }
                } else if (parametersMap.containsKey("startDate")) {
                    if (checkDate((String) parametersMap.get("startDate"))) {
                        String startDate = (String) parametersMap.get("startDate");
                        listSql = listSql + " AND cdate >= '" + startDate + "' ";
                    }
                } else if (parametersMap.containsKey("endDate")) {
                    if (checkDate((String) parametersMap.get("endDate"))) {
                        String endDate = (String) parametersMap.get("endDate");
                        listSql = listSql + " AND cdate <= '" + endDate + "' ";
                    }
                }
            }
            listSql = listSql +
                    "        GROUP BY comment_id " +
                    "        ORDER BY latestId DESC ) cr  " +
                    "       LEFT JOIN MG_COMMENT mc ON mc.comment_id = cr.comment_id " +
                    "       LEFT JOIN MG_CARD mcd ON mcd.card_id = mc.card_id " +
                    "       LEFT JOIN USER u ON u.uid = mc.uid " +
                    " WHERE 1 = 1 ";
            if (parametersMap != null) {
                if (parametersMap.containsKey("keyword")) {
                    listSql = listSql + " AND mc.text LIKE '%" + parametersMap.get("keyword") + "%' ";
                } else if (parametersMap.containsKey("email")) {
                    listSql = listSql + " AND u.email = '" + parametersMap.get("email") + "' ";
                } else if (parametersMap.containsKey("nickName")) {
                    listSql = listSql + " AND u.name = '" + parametersMap.get("nickName") + "' ";
                } else if (parametersMap.containsKey("uid")) {
                    listSql = listSql + " AND u.uid = '" + parametersMap.get("uid") + "' ";
                }
            }
            listSql = listSql + "AND mc.status='ACTV' " +
                    " LIMIT " + limit +
                    " OFFSET " + offset;
            listQuery = entityManager.createNativeQuery(listSql);
            objecArraytList = listQuery.getResultList();
        }
        if (objecArraytList == null) {
            throw new BadRequestException();
        }
        List<CommentResult> commentResultList = new ArrayList<CommentResult>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            CommentResult commentResult = new CommentResult();
            commentResult.setCommentId(Long.valueOf(obj[0].toString()));
            commentResult.setReportCount(Integer.valueOf(obj[1].toString()));
            if (obj[2] != null) {
                commentResult.setCommentType(obj[2].toString());
            }
            if (obj[3] != null) {
                commentResult.setcDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[3].toString()));
            }
            if (obj[4] != null) {
                commentResult.setUid(Long.valueOf(obj[4].toString()));
            }
            if (obj[5] != null) {
                commentResult.setNickName(obj[5].toString());
            }
            if (obj[6] != null) {
                commentResult.setCommentText(obj[6].toString());
            }
            if (obj[7] != null) {
                commentResult.setReportType(obj[7].toString());
            }
            if (obj[8] != null) {
                commentResult.setReportDescription(obj[8].toString());
            }
            if (obj[9] != null) {
                commentResult.setContentId(Long.valueOf(obj[9].toString()));
            }
            if (obj[10] != null) {
                commentResult.setCardId(Long.valueOf(obj[10].toString()));
                if (obj[11] != null) {
                    commentResult.setCardOrdering(Integer.parseInt(obj[11].toString()));
                }
            }
            commentResult.setLikeCount(Integer.valueOf(obj[12].toString()));
            if (obj[13] != null) {
                commentResult.setRecommentCount(Integer.valueOf(obj[13].toString()));
            }
            if (obj[15] != null) {
                commentResult.setParentCommentId(Long.valueOf(obj[15].toString()));
            }
            if (obj[16] != null) {
                commentResult.setInHouseReportCount(Integer.parseInt(obj[16].toString()));
            }
            if (obj[17] != null) {
                commentResult.setReportId(Long.parseLong(obj[17].toString()));
            }
            commentResult.setSlangType(findSlangType(commentResult.getCommentText()));
            commentResultList.add(commentResult);
        }
        log.info(commentResultList.toString());
        Pageable pageable = new PageRequest(offset / limit, limit);

        return new PageImpl<CommentResult>(commentResultList, pageable, count);
    }

    @Override
    public Integer findLikeCountByCommentId(Long commentId) throws Exception {
        JPAQuery likeCountQuery = new JPAQuery(entityManager);
        QMgCommentLike mgCommentLik = QMgCommentLike.mgCommentLike;

        likeCountQuery.from(mgCommentLik).where(mgCommentLik.id.commentId.eq(commentId));
        Long likeCount = likeCountQuery.count();

        return likeCount.intValue();
    }

    @Override
    public Integer findRecommentCountByCommentId(Long commentId) throws Exception {
        JPAQuery recommentCountQuery = new JPAQuery(entityManager);
        QMgComment mgComment = QMgComment.mgComment;
        Long recommentCount = 0L;
        if (null != commentId) {
            recommentCountQuery.from(mgComment).where(mgComment.parentCommentId.eq(commentId));
            recommentCount = recommentCountQuery.count();
        }

        return recommentCount.intValue();
    }

    @Override
    public String findSlangType(String commentText) throws Exception {
        String type = "-";
        if (commentText == null)
            return type;
        for (SlangWord s : list) {
            if (commentText.contains(s.getSlang())) {
                if (s.getType().equals("A") && (type.equals("-") || type.equals("B") || type.equals("C"))) {
                    type = s.getType();
                    return type;
                } else if (s.getType().equals("B") && (type.equals("-") || type.equals("C"))) {
                    type = s.getType();
                } else if (s.getType().equals("C") && type.equals("-")) {
                    type = s.getType();
                }
            }
        }
        return type;
    }

    @Override
    public String findAllReportType(Long commentId) throws Exception {
        JPAQuery listQuery = new JPAQuery(entityManager);
        QCommentReport commentReport = QCommentReport.commentReport;

        listQuery.from(commentReport).where(commentReport.commentId.eq(commentId)).groupBy(commentReport.reportType);
        List<Tuple> list = listQuery.list
                (commentReport.count(),
                        commentReport.reportType);
        StringBuilder result = new StringBuilder();
        for (Tuple tuple : list) {
            result.append(",");
            result.append(tuple.get(commentReport.reportType));
        }
        return result.toString();
    }

    void decreaseCommentCountAndCardCount(Long contentsId, Long cardId, Long parentCommentId) {
        if (parentCommentId != null) {
            return;
        }
        //if value is 0, decreas
        String restAppContentsKey = "CONTENTS_COMMENT_COUNT";
        String restWebContentsKey = "CONTENTS_COMMENT_COUNT_WEB";
        String hashContentsKey = contentsId.toString();
        String restContentsKeySuffix = ":" + contentsId / 1000;
        if (hashOperations.hasKey(restAppContentsKey + restContentsKeySuffix, hashContentsKey)) {
            String commentCountString = (String) hashOperations.get(restAppContentsKey + restContentsKeySuffix, hashContentsKey);
            Integer commentCount = Integer.valueOf(commentCountString);
            if (commentCount > 0) {
                hashOperations.increment(restAppContentsKey + restContentsKeySuffix, hashContentsKey, -1);
            } else {
                if (hashOperations.hasKey(restWebContentsKey + restContentsKeySuffix, hashContentsKey)) {
                    String commentWebCountString = (String) hashOperations.get(restWebContentsKey + restContentsKeySuffix, hashContentsKey);
                    Integer commentWebCount = Integer.valueOf(commentWebCountString);
                    if (commentWebCount > 0) {
                        hashOperations.increment(restWebContentsKey + restContentsKeySuffix, hashContentsKey, -1);
                    }
                }
            }
        } else if (hashOperations.hasKey(restWebContentsKey + restContentsKeySuffix, hashContentsKey)) {
            String commentWebCountString = (String) hashOperations.get(restWebContentsKey + restContentsKeySuffix, hashContentsKey);
            Integer commentWebCount = Integer.valueOf(commentWebCountString);
            if (commentWebCount > 0) {
                hashOperations.increment(restWebContentsKey + restContentsKeySuffix, hashContentsKey, -1);
            }
        }
        if (cardId == null) {
            return;
        }
        String restAppCardKey = "CARD_COMMENT_COUNT";
        String restWebCardKey = "CARD_COMMENT_COUNT_WEB";
        String hashCardKey = cardId.toString();
        String restCardSuffix = ":" + cardId / 1000;
        if (hashOperations.hasKey(restAppCardKey + restCardSuffix, hashCardKey)) {
            String commentCountString = (String) hashOperations.get(restAppCardKey + restCardSuffix, hashCardKey);
            Integer commentCount = Integer.valueOf(commentCountString);
            if (commentCount > 0) {
                hashOperations.increment(restAppCardKey + restCardSuffix, hashCardKey, -1);
            } else {
                if (hashOperations.hasKey(restWebCardKey + restCardSuffix, hashCardKey)) {
                    String commentWebCountString = (String) hashOperations.get(restWebCardKey + restCardSuffix, hashCardKey);
                    Integer commentWebCount = Integer.valueOf(commentWebCountString);
                    if (commentWebCount > 0) {
                        hashOperations.increment(restWebCardKey + restCardSuffix, hashCardKey, -1);
                    }
                }
            }
        } else if (hashOperations.hasKey(restWebCardKey + restCardSuffix, hashCardKey)) {
            String commentWebCountString = (String) hashOperations.get(restWebCardKey + restCardSuffix, hashCardKey);
            Integer commentWebCount = Integer.valueOf(commentWebCountString);
            if (commentWebCount > 0) {
                hashOperations.increment(restWebCardKey + restCardSuffix, hashCardKey, -1);
            }
        }
    }

    @Override
    public void updateMgCommentStatus(Long commentId, String status) throws Exception {
        if (Const.Status.DEL.equals(status) || Const.Status.HIDD.equals(status)) {
            QMgComment qMgComment = QMgComment.mgComment;
            JPAUpdateClause mgCommentUpdate = new JPAUpdateClause(entityManager, qMgComment);
            mgCommentUpdate
                    .where(qMgComment.commentId.eq(commentId))
                    .set(qMgComment.status, status).execute();

            JPAQuery jpaQuery = new JPAQuery(entityManager);
            MgComment mgComment = jpaQuery.from(qMgComment).where(qMgComment.commentId.eq(commentId), qMgComment.status.eq(status)).uniqueResult(qMgComment);
            if (mgComment != null) {
                decreaseCommentCountAndCardCount(mgComment.getContentsId(), mgComment.getCardId(), mgComment.getParentCommentId());
            }
            QCommentReport qCommentReport = QCommentReport.commentReport;
            JPAUpdateClause qCommentReportUpdate = new JPAUpdateClause(entityManager, qCommentReport);
            qCommentReportUpdate
                    .where(qCommentReport.commentId.eq(commentId))
                    .where(qCommentReport.handleResult.isNull())
                    .set(qCommentReport.handleResult, status).execute();
        } else if (Const.Status.PASS.equals(status)) {
            QCommentReport qCommentReport = QCommentReport.commentReport;
            JPAUpdateClause qCommentReportUpdate = new JPAUpdateClause(entityManager, qCommentReport);
            qCommentReportUpdate
                    .where(qCommentReport.commentId.eq(commentId))
                    .where(qCommentReport.handleResult.isNull())
                    .set(qCommentReport.handleResult, status).execute();
        } else if (Const.Status.ACTV.equals(status)) {
            QMgComment qMgComment = QMgComment.mgComment;
            JPAUpdateClause mgCommentUpdate = new JPAUpdateClause(entityManager, qMgComment);
            mgCommentUpdate
                    .where(qMgComment.commentId.eq(commentId))
                    .set(qMgComment.status, status).execute();
        }
    }

    @Override
    public void updateMgCommentStatus(String[] commentIds, String status) throws Exception {
        QMgComment qMgComment = QMgComment.mgComment;
        QCommentReport qCommentReport = QCommentReport.commentReport;
            /*
            condition
             */
        BooleanExpression booleanExpression = null;
        BooleanExpression booleanExpression1 = null;
        for (String commentId : commentIds) {
            if (booleanExpression == null) {
                booleanExpression = qMgComment.commentId.eq(Long.valueOf(commentId));
                booleanExpression1 = qCommentReport.commentId.eq(Long.valueOf(commentId));
            } else {
                booleanExpression = booleanExpression.or(qMgComment.commentId.eq(Long.valueOf(commentId)));
                booleanExpression1 = booleanExpression1.or(qCommentReport.commentId.eq(Long.valueOf(commentId)));
            }
        }
        if (Const.Status.DEL.equals(status) || Const.Status.HIDD.equals(status)) {
             /*
             change commentReport status to DEL/HIDD
             */
            JPAUpdateClause qCommentReportUpdate = new JPAUpdateClause(entityManager, qCommentReport);
            qCommentReportUpdate.where(booleanExpression1);
            qCommentReportUpdate.where(qCommentReport.handleResult.isNull());
            qCommentReportUpdate.set(qCommentReport.handleResult, status).execute();
            /*
            when change commentReport status to DEL/HIDD,we should change mgComment's status
             */
            JPAUpdateClause mgCommentUpdate = new JPAUpdateClause(entityManager, qMgComment);
            mgCommentUpdate.where(booleanExpression);
            mgCommentUpdate.set(qMgComment.status, status).execute();
             /*
            decrease comment count of redis
             */
            JPAQuery jpaQuery = new JPAQuery(entityManager);
            List<MgComment> mgCommentList = jpaQuery.from(qMgComment).where(booleanExpression, qMgComment.status.eq(status)).list(qMgComment);
            for (MgComment mgComment : mgCommentList) {
                if (mgComment != null) {
                    decreaseCommentCountAndCardCount(mgComment.getContentsId(), mgComment.getCardId(), mgComment.getParentCommentId());
                }
            }

        } else if (Const.Status.PASS.equals(status)) {
             /*
             change commentReport status to DEL/HIDD
             */
            JPAUpdateClause qCommentReportUpdate = new JPAUpdateClause(entityManager, qCommentReport);
            qCommentReportUpdate.where(booleanExpression1);
            qCommentReportUpdate.where(qCommentReport.handleResult.isNull());
            qCommentReportUpdate.set(qCommentReport.handleResult, status).execute();
        } else if (Const.Status.ACTV.equals(status)) {
            /*
            we only change mgComment's status to ACTV
             */
            JPAUpdateClause mgCommentUpdate = new JPAUpdateClause(entityManager, qMgComment);
            mgCommentUpdate.where(booleanExpression);
            mgCommentUpdate.set(qMgComment.status, status).execute();
            //TODO incrouse comment count of redis
        }
    }

    @Override
    public ReportTypeCountResult findReportTypeCount(Long uid) throws Exception {
        String listSql = "SELECT cr.report_type,count(*) " +
                " FROM (SELECT comment_id,uid FROM MG_COMMENT where uid=" +
                uid +
                " and status='DEL') dcm" +
                " LEFT JOIN COMMENT_REPORT cr on dcm.comment_id=cr.comment_id" +
                " GROUP BY cr.report_type";
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();
        if (objecArraytList == null) {
            throw new BadRequestException();
        }
        ReportTypeCountResult reportTypeCountResult = new ReportTypeCountResult();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            if (obj[0] == null || obj[0].toString().equals(Const.ReportType.RT999)) {
                reportTypeCountResult.setRt999Count(reportTypeCountResult.getRt999Count() + Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().equals(Const.ReportType.RT100)) {
                reportTypeCountResult.setRt100Count(Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().equals(Const.ReportType.RT200)) {
                reportTypeCountResult.setRt200Count(Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().equals(Const.ReportType.RT300)) {
                reportTypeCountResult.setRt300Count(Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().equals(Const.ReportType.RT400)) {
                reportTypeCountResult.setRt400Count(Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().equals(Const.ReportType.RT500)) {
                reportTypeCountResult.setRt500Count(Integer.parseInt(obj[1].toString()));
            } else if (obj[0].toString().equals(Const.ReportType.RT600)) {
                reportTypeCountResult.setRt600Count(Integer.parseInt(obj[1].toString()));
            }
        }
        return reportTypeCountResult;
    }

    @Override
    public Integer findBestCommentCount(Long uid) throws Exception {
        String listSql = " SELECT count(*) finalBestCommeCount FROM " +
                " (SELECT mgl.comment_id,count(*)+rmc.replyCount bestCommeCount " +
                " FROM  MG_COMMENT_LIKE mgl " +
                " LEFT JOIN (SELECT comment_id FROM MG_COMMENT WHERE uid= " +
                uid +
                " ) mgu on mgl.comment_id = mgu.comment_id " +
                " LEFT JOIN (SELECT parent_comment_id,count(*)*5 replyCount FROM MG_COMMENT " +
                "      LEFT JOIN (SELECT comment_id FROM MG_COMMENT WHERE uid= " +
                uid +
                " ) mgu on parent_comment_id = mgu.comment_id " +
                "      WHERE parent_comment_id = mgu.comment_id " +
                "      GROUP BY parent_comment_id) rmc on rmc.parent_comment_id = mgl.comment_id " +
                " WHERE mgl.comment_id = mgu.comment_id " +
                " GROUP BY mgl.comment_id) bestCommentCountResult " +
                " WHERE bestCommentCountResult.bestCommeCount >100 ";
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();
        if (objecArraytList == null) {
            throw new BadRequestException();
        }
        int finalResultCount = 0;
        if (objecArraytList.size() > 0) {
            finalResultCount = Integer.valueOf(objecArraytList.get(0).toString());
        }
        return finalResultCount;
    }

    @Override
    public Page<CommentResult> findAllCommentByUid(Integer offset, Integer limit, String q) throws Exception {

        List<String> searchBy = new ArrayList<String>();
        List<String> keyword = new ArrayList<String>();
        if ((q != null && !q.isEmpty())) {
            if (StringUtils.isNotBlank(q)) {
                String dq = URLDecoder.decode(q, "utf-8");
                String[] conditions = dq.split(",");
                if (conditions != null & conditions.length > 0) {
                    for (String c : conditions) {
                        String[] arr = c.split("=");
                        searchBy.add(arr[0]);
                        keyword.add(arr[1]);
                    }
                }
            }
        }

        String countSql = "SELECT " +
                " COUNT(mc.comment_id) " +
                "FROM MG_COMMENT mc WHERE uid = " +
                keyword.get(0)
//                +
//                " AND (SELECT COUNT(comment_id) " +
//                " FROM COMMENT_REPORT WHERE comment_id = mc.comment_id )>0"
                ;

        Query countQuery = entityManager.createNativeQuery(countSql);
        List objecCountList = countQuery.getResultList();
        if (objecCountList == null) {
            throw new BadRequestException();
        }
        int count = 0;
        if (objecCountList.size() > 0) {
            count = Integer.valueOf(objecCountList.get(0).toString());
        }

        String listSql = "SELECT " +
                " mc.cdate," +
                " mc.comment_id," +
                "(SELECT COUNT(comment_id) FROM COMMENT_REPORT WHERE comment_id = mc.comment_id) report_count," +
                " u.name," +
                " mc.text," +
                " (SELECT COUNT(*) FROM MG_COMMENT_LIKE cl WHERE cl.comment_id = mc.comment_id) likeCount," +
                " (SELECT COUNT(*) FROM MG_COMMENT cc WHERE cc.parent_comment_id = mc.comment_id AND cc.status = 'ACTV') childCommentCount," +
                " mc.parent_comment_id," +
                " mc.status," +
                " (SELECT handle_result FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) handle_result," +
                " (SELECT monitor_id FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) monitor_id," +
                " (SELECT count(comment_id) FROM COMMENT_REPORT WHERE comment_id = mc.comment_id AND handle_result IS NULL) not_handle_report_count," +
                " (SELECT ordering FROM MG_CARD WHERE card_id = mc.card_id) cardOrdering, " +
                "  mc.contents_id," +
                "  mc.card_id, " +
                " (SELECT name FROM MONITOR where monitor_id = (SELECT monitor_id FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1)) monitor_name" +
                " FROM" +
                " MG_COMMENT mc" +
                " LEFT JOIN USER u ON u.uid = " +
                keyword.get(0) +
                " WHERE  mc.uid = " +
                keyword.get(0) +
//                " AND (SELECT COUNT(comment_id) FROM COMMENT_REPORT WHERE comment_id = mc.comment_id )>0" +
                " ORDER BY comment_id DESC " +
                " LIMIT " + limit +
                " OFFSET " + offset;

        Query listQuery = entityManager.createNativeQuery(listSql);

        List objecArraytList = listQuery.getResultList();
        if (objecArraytList == null) {
            throw new BadRequestException();
        }
        List<CommentResult> commentResultList = new ArrayList<CommentResult>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            CommentResult commentResult = new CommentResult();
            if (obj[0] != null) {
                commentResult.setcDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[0].toString()));
            }
            if (obj[1] != null) {
                commentResult.setCommentId(Long.valueOf(obj[1].toString()));
            }
            if (obj[2] != null) {
                commentResult.setReportCount(Integer.valueOf(obj[2].toString()));
            }
            if (obj[3] != null) {
                commentResult.setNickName(obj[3].toString());
            }
            if (obj[4] != null) {
                commentResult.setCommentText(obj[4].toString());
            }
            if (obj[5] != null) {
                commentResult.setLikeCount(Integer.valueOf(obj[5].toString()));
            }
            if (obj[6] != null) {
                commentResult.setRecommentCount(Integer.valueOf(obj[6].toString()));
            }
            if (obj[7] != null) {
                commentResult.setCommentType("RC");
                commentResult.setParentCommentId(Long.valueOf(obj[7].toString()));
            } else {
                commentResult.setCommentType("C");
            }
            if (obj[8] != null) {
                commentResult.setStatus(obj[8].toString());
            }

            if (obj[9] != null) {
                commentResult.setHandleResult(obj[9].toString());
            }
            if (obj[10] != null) {
                commentResult.setMonitorId(Long.valueOf(obj[10].toString()));
            }
            commentResult.setNotHandleReportCount(Integer.valueOf(obj[11].toString()));
            if (obj[12] != null) {
                commentResult.setCardOrdering(Integer.valueOf(obj[12].toString()));
            }
            if (obj[13] != null) {
                commentResult.setContentId(Long.valueOf(obj[13].toString()));
            }
            if (obj[14] != null) {
                commentResult.setCardId(Long.valueOf(obj[14].toString()));
            }
            if (obj[15] != null) {
                commentResult.setMonitorName(obj[15].toString());
            }
            commentResultList.add(commentResult);
        }
        Pageable pageable = new PageRequest(offset / limit, limit);

        return new PageImpl<CommentResult>(commentResultList, pageable, count);
    }

    @Override
    public Page<CommentResult> findAllComments(Integer offset, Integer limit, String q) throws Exception {
        JPAQuery slangWordListQuery = new JPAQuery(entityManager);
        QSlangWord qSlangWord = QSlangWord.slangWord;
        slangWordListQuery.from(qSlangWord);
        list = slangWordListQuery.list(qSlangWord);

        HashMap<String, Object> parametersMap = new HashMap<>();
        if (StringUtils.isNotBlank(q)) {
            q = URLDecoder.decode(q, "utf-8");
            String[] conditions = q.split(",");
            if (conditions != null & conditions.length > 0) {
                for (String parameter : conditions) {
                    String[] values = parameter.split("=");
                    if (values.length > 1) {
                        parametersMap.put(values[0], values[1]);
                    }
                }
            }
        }
        Integer count = 0;
        List objecArraytList = null;
        Long cardId = null;
        Long contentsId = null;
        if (parametersMap.containsKey("cardId")) {
            String cardIdSting = (String) parametersMap.get("cardId");
            cardId = Long.valueOf(cardIdSting);
        }
        if (parametersMap.containsKey("contentsId")) {
            String contentsIdSting = (String) parametersMap.get("contentsId");
            contentsId = Long.valueOf(contentsIdSting);
        }
        log.info(parametersMap.toString());
        //seprate two to way to search comment
        //1,if exactly search by name and email just use mysql
        if (
//                (parametersMap.containsKey("startDate") || parametersMap.containsKey("endDate"))&&
                        (parametersMap.containsKey("email") && parametersMap.containsKey("nickName")) &&
                        (parametersMap.containsKey("email") && parametersMap.containsKey("keyword"))&&
                        (parametersMap.containsKey("keyword") && parametersMap.containsKey("nickName"))) {
            throw new BadRequestException();
        } else if (cardId != null || contentsId != null || !elasticSearchTemplate.getElasticsearchSwitch()
//                || (parametersMap.containsKey("email") && StringUtils.isNotBlank((String) parametersMap.get("email")))
//                || (parametersMap.containsKey("nickName") && StringUtils.isNotBlank((String) parametersMap.get("nickName")))
                ) {
            String countSql = "SELECT " +
                    " COUNT(mc.comment_id) " +
                    " FROM MG_COMMENT mc " +
                    " LEFT JOIN USER u on u.uid=mc.uid" +
                    " WHERE 1=1";
            if (parametersMap.containsKey("contentsId") && !parametersMap.containsKey("cardId")) {
                countSql = countSql + " AND mc.contents_id = '" + parametersMap.get("contentsId") + "' ";
            } else if (parametersMap.containsKey("cardId")) {
                countSql = countSql + " AND mc.card_id = '" + parametersMap.get("cardId") + "' ";
            }
            countSql = countSql + " AND mc.status = 'ACTV' ";
            countSql = countSql + " AND (((SELECT  count(*) count FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id) = 0 ) or (SELECT  handle_result FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) NOT IN ('PASS','DEL', 'HIDD'))";
            if (parametersMap.containsKey("email")) {
                countSql = countSql + " AND u.email LIKE '%" + parametersMap.get("email") + "%' ";
            } else if (parametersMap.containsKey("nickName")) {
                countSql = countSql + " AND u.name LIKE '%" + parametersMap.get("nickName") + "%' ";
            } else if (parametersMap.containsKey("keyword")) {
                countSql = countSql + " AND mc.text LIKE '%" + parametersMap.get("keyword") + "%' ";
            }

            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    countSql = countSql +
                            " AND (mc.cdate BETWEEN '" + startDate + "'" +
                            " AND  '" + endDate + "')";
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    countSql = countSql + " AND mc.cdate >= '" + startDate + "' ";
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    countSql = countSql + " AND mc.cdate <= '" + endDate + "' ";
                }
            }

            Query countQuery = entityManager.createNativeQuery(countSql);
            List objecCountList = countQuery.getResultList();
            if (objecCountList == null) {
                throw new BadRequestException();
            }
            if (objecCountList.size() > 0) {
                count = Integer.valueOf(objecCountList.get(0).toString());
            }

            String listSql =
                    "SELECT " +
                            " mc.cdate," +
                            " (SELECT COUNT(comment_id) FROM COMMENT_REPORT WHERE comment_id = mc.comment_id) report_count, " +
                            " (SELECT name from USER where mc.uid = uid) name, " +
                            " mc.text, " +
                            " (SELECT COUNT(*) FROM MG_COMMENT_LIKE cl WHERE cl.comment_id = mc.comment_id) likeCount, " +
                            " (SELECT COUNT(*) FROM MG_COMMENT cc WHERE cc.parent_comment_id = mc.comment_id AND cc.status = 'ACTV') childCommentCount, " +
                            " mc.parent_comment_id," +
                            " mc.comment_id," +
                            " mc.uid, " +
                            " (SELECT handle_result FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) handle_result, " +
                            " (SELECT monitor_id FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) monitor_id ," +
                            " (SELECT count(comment_id) FROM COMMENT_REPORT WHERE comment_id = mc.comment_id AND handle_result IS NULL) not_handle_report_count," +
                            " (SELECT ordering FROM MG_CARD WHERE card_id = mc.card_id) cardOrdering, " +
                            "  mc.contents_id," +
                            "  mc.status, ";
            listSql = listSql +
                    "       (SELECT count(x.id) FROM COMMENT_REPORT x LEFT JOIN USER u ON x.uid=u.uid WHERE x.comment_id=mc.comment_id ";
            for (int i = 0; i < Const.InHouseUsers.size(); i++) {
                String email = Const.InHouseUsers.get(i);
                if (i == 0) {
                    listSql = listSql + " AND (u.email='" + email + "' ";
                } else if (i == Const.InHouseUsers.size() - 1) {
                    listSql = listSql + " OR u.email='" + email + "') ";
                } else {
                    listSql = listSql + "    OR u.email='" + email + "'";
                }
            }
            listSql = listSql +
                    ") inHouseReportCount, " +
                    "  mc.card_id," +
                    " (SELECT name FROM MONITOR where monitor_id = (SELECT monitor_id FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1)) monitor_name " +
                    " FROM MG_COMMENT mc " +
                    " LEFT JOIN USER u on u.uid=mc.uid" +
                    " WHERE 1=1";
            if (parametersMap != null) {
                if (parametersMap.containsKey("contentsId") && !parametersMap.containsKey("cardId")) {
                    listSql = listSql + " AND mc.contents_id = '" + parametersMap.get("contentsId") + "' ";
                } else if (parametersMap.containsKey("cardId")) {
                    listSql = listSql + " AND mc.card_id = '" + parametersMap.get("cardId") + "' ";
                }
                listSql = listSql + " AND mc.status = 'ACTV' ";
                listSql = listSql + " AND (((SELECT  count(*) count FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id) = 0 ) or (SELECT  handle_result FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) NOT IN ('PASS','DEL', 'HIDD'))";
                if (parametersMap.containsKey("email")) {
                    listSql = listSql + " AND u.email LIKE '%" + parametersMap.get("email") + "%' ";
                } else if (parametersMap.containsKey("nickName")) {
                    listSql = listSql + " AND u.name LIKE '%" + parametersMap.get("nickName") + "%' ";
                } else if (parametersMap.containsKey("keyword")) {
                    listSql = listSql + " AND mc.text LIKE '%" + parametersMap.get("keyword") + "%' ";
                }

                if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                    if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                        String startDate = (String) parametersMap.get("startDate");
                        String endDate = (String) parametersMap.get("endDate");
                        listSql = listSql +
                                " AND (mc.cdate BETWEEN '" + startDate + "'" +
                                " AND  '" + endDate + "')";
                    }
                } else if (parametersMap.containsKey("startDate")) {
                    if (checkDate((String) parametersMap.get("startDate"))) {
                        String startDate = (String) parametersMap.get("startDate");
                        listSql = listSql + " AND mc.cdate >= '" + startDate + "' ";
                    }
                } else if (parametersMap.containsKey("endDate")) {
                    if (checkDate((String) parametersMap.get("endDate"))) {
                        String endDate = (String) parametersMap.get("endDate");
                        listSql = listSql + " AND mc.cdate <= '" + endDate + "' ";
                    }
                }
            }
            listSql = listSql + "" +
                    " ORDER BY comment_id DESC" +
                    " LIMIT :limit  OFFSET :offset ";
            Query listQuery = entityManager.createNativeQuery(listSql);
            listQuery.setParameter("limit", limit);
            listQuery.setParameter("offset", offset);
            objecArraytList = listQuery.getResultList();

        } else
//        if ((parametersMap.containsKey("keyword") && StringUtils.isNotBlank((String) parametersMap.get("keyword"))) ||
//                (!parametersMap.containsKey("keyword") && !parametersMap.containsKey("nickName") && !parametersMap.containsKey("email")))
        {
            //2,if vague search by keyword of comment text use elasticsearch get comment ids first
            //then search mysql.(even there is 50 result,just cost less than 1s)
            Date startDate = null;
            Date endDate = null;
            if (parametersMap.containsKey("startDate") && checkDate((String) parametersMap.get("startDate"))) {
                startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) parametersMap.get("startDate") + " 00:00:00");
            }
            if (parametersMap.containsKey("endDate") && checkDate((String) parametersMap.get("endDate"))) {
                endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) parametersMap.get("endDate") + " 00:00:00");
            }
            String field = "keyword";
            String value = "";
            if (parametersMap.containsKey("email")) {
                field = "email";
                value = (String) parametersMap.get("email");
            } else if (parametersMap.containsKey("nickName")) {
                field = "nickName";
                value = (String) parametersMap.get("nickName");
            } else if (parametersMap.containsKey("keyword")) {
                field = "keyword";
                value = (String) parametersMap.get("keyword");
            }
            SearchHits hits = elasticSearchTemplate.searchMgComment(
                    "allets_common",
                    "MG_COMMENT",
                    field,
                    value,
                    startDate,//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-11-24 00:00:00"),
                    endDate,//new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-11-27 00:00:00"),
                    offset, limit);
            count = (int) hits.totalHits();
            log.info(String.valueOf(count));
            if (hits.getHits().length > 0) {
                String listCommentIdsSql =
                        "SELECT " +
                                " mc.cdate," +
                                " (SELECT COUNT(comment_id) FROM COMMENT_REPORT WHERE comment_id = mc.comment_id) report_count, " +
                                " (SELECT name from USER where mc.uid = uid) name, " +
                                " mc.text, " +
                                " (SELECT COUNT(*) FROM MG_COMMENT_LIKE cl WHERE cl.comment_id = mc.comment_id) likeCount, " +
                                " (SELECT COUNT(*) FROM MG_COMMENT cc WHERE cc.parent_comment_id = mc.comment_id AND cc.status = 'ACTV') childCommentCount, " +
                                " mc.parent_comment_id," +
                                " mc.comment_id," +
                                " mc.uid, " +
                                " (SELECT handle_result FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) handle_result, " +
                                " (SELECT monitor_id FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1) monitor_id ," +
                                " (SELECT count(comment_id) FROM COMMENT_REPORT WHERE comment_id = mc.comment_id AND handle_result IS NULL) not_handle_report_count," +
                                " (SELECT ordering FROM MG_CARD WHERE card_id = mc.card_id) cardOrdering, " +
                                "  mc.contents_id," +
                                "  mc.status, ";
                listCommentIdsSql = listCommentIdsSql +
                        "       (SELECT count(x.id) FROM COMMENT_REPORT x LEFT JOIN USER u ON x.uid=u.uid WHERE x.comment_id=mc.comment_id ";
                for (int i = 0; i < Const.InHouseUsers.size(); i++) {
                    String email = Const.InHouseUsers.get(i);
                    if (i == 0) {
                        listCommentIdsSql = listCommentIdsSql + " AND (u.email='" + email + "' ";
                    } else if (i == Const.InHouseUsers.size() - 1) {
                        listCommentIdsSql = listCommentIdsSql + " OR u.email='" + email + "') ";
                    } else {
                        listCommentIdsSql = listCommentIdsSql + " OR u.email='" + email + "'";
                    }
                }
                listCommentIdsSql = listCommentIdsSql +
                        ") inHouseReportCount, " +
                        "  mc.card_id, " +
                        " (SELECT name FROM MONITOR where monitor_id = (SELECT monitor_id FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id ORDER BY id DESC LIMIT 1)) monitor_name"+
                        " FROM MG_COMMENT mc " +
                        " LEFT JOIN USER u on u.uid=mc.uid" +
                        " WHERE 1=1";
                for (int i = 0; i < hits.getHits().length; i++) {
                    Long commentId = Long.valueOf(hits.getHits()[i].getSource().get("comment_id").toString());
                    if (i == 0) {
                        listCommentIdsSql = listCommentIdsSql + " AND ";
                    } else {
                        listCommentIdsSql = listCommentIdsSql + " OR ";
                    }
                    listCommentIdsSql = listCommentIdsSql + " mc.comment_id = " + commentId;
                }
                listCommentIdsSql = listCommentIdsSql + " ORDER BY comment_id DESC";
                Query listQuery = entityManager.createNativeQuery(listCommentIdsSql);
                objecArraytList = listQuery.getResultList();
            } else {
                objecArraytList = new ArrayList<>();
            }
        }


        if (objecArraytList == null) {
            throw new BadRequestException();
        }
        List<CommentResult> commentResultList = new ArrayList<CommentResult>();
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            CommentResult commentResult = new CommentResult();
            if (obj[0] != null) {
                commentResult.setcDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[0].toString()));
            }
            if (obj[1] != null) {
                commentResult.setReportCount(Integer.valueOf(obj[1].toString()));
            }
            if (obj[2] != null) {
                commentResult.setNickName(obj[2].toString());
            }
            if (obj[3] != null) {
                commentResult.setCommentText(obj[3].toString());
            }
            if (obj[4] != null) {
                commentResult.setLikeCount(Integer.valueOf(obj[4].toString()));
            }
            if (obj[5] != null) {
                commentResult.setRecommentCount(Integer.valueOf(obj[5].toString()));
            }
            if (obj[6] != null) {
                commentResult.setParentCommentId(Long.valueOf(obj[6].toString()));
                commentResult.setCommentType("RC");
            } else {
                commentResult.setCommentType("C");
            }
            if (obj[7] != null) {
                commentResult.setCommentId(Long.valueOf(obj[7].toString()));
            }
            if (obj[8] != null) {
                commentResult.setUid(Long.valueOf(obj[8].toString()));
            }
            if (obj[9] != null) {
                commentResult.setHandleResult(obj[9].toString());
            }
            if (obj[10] != null) {
                commentResult.setMonitorId(Long.valueOf(obj[10].toString()));
            }
            commentResult.setNotHandleReportCount(Integer.valueOf(obj[11].toString()));
            if (obj[12] != null) {
                commentResult.setCardOrdering(Integer.valueOf(obj[12].toString()));
            }
            if (obj[13] != null) {
                commentResult.setContentId(Long.valueOf(obj[13].toString()));
            }
            if (obj[14] != null) {
                commentResult.setStatus(obj[14].toString());
            }
            if (obj[15] != null) {
                commentResult.setInHouseReportCount(Integer.valueOf(obj[15].toString()));
            }
            if (obj[16] != null) {
                commentResult.setCardId(Long.valueOf(obj[16].toString()));
            }
            if (obj[17] != null) {
                commentResult.setMonitorName(obj[17].toString());
            }
            commentResult.setSlangType(findSlangType(commentResult.getCommentText()));
            commentResultList.add(commentResult);
        }
        Pageable pageable = new PageRequest(offset / limit, limit);

        return new PageImpl<CommentResult>(commentResultList, pageable, count);
    }


    public Boolean checkDate(String date) {
        return date != null && !date.isEmpty() & !date.equals("Invalid Date");
    }

    @Override
    public void updateMgCommentStatusForAllComments(String[] commentIds, String status) throws Exception {
        QMgComment qMgComment = QMgComment.mgComment;
        QCommentReport qCommentReport = QCommentReport.commentReport;
            /*
            condition
             */
        BooleanExpression booleanExpression = null;
        BooleanExpression booleanExpression1 = null;
        for (String commentId : commentIds) {
            if (booleanExpression == null) {
                booleanExpression = qMgComment.commentId.eq(Long.valueOf(commentId));
                booleanExpression1 = qCommentReport.commentId.eq(Long.valueOf(commentId));
            } else {
                booleanExpression = booleanExpression.or(qMgComment.commentId.eq(Long.valueOf(commentId)));
                booleanExpression1 = booleanExpression1.or(qCommentReport.commentId.eq(Long.valueOf(commentId)));
            }
        }
        if (Const.Status.DEL.equals(status) || Const.Status.HIDD.equals(status)) {
             /*
             we should change mgComment's status to DEL/HIDD
             */
            JPAUpdateClause mgCommentUpdate = new JPAUpdateClause(entityManager, qMgComment);
            mgCommentUpdate.where(booleanExpression);
            mgCommentUpdate.set(qMgComment.status, status).execute();
             /*
             change commentReport status to DEL/HIDD
             */
            JPAUpdateClause qCommentReportUpdate = new JPAUpdateClause(entityManager, qCommentReport);
            qCommentReportUpdate.where(booleanExpression1);
            qCommentReportUpdate.where(qCommentReport.handleResult.isNull());
            qCommentReportUpdate.set(qCommentReport.handleResult, status).execute();
             /*
            decrease comment count of redis
             */
            JPAQuery jpaQuery = new JPAQuery(entityManager);
            List<MgComment> mgCommentList = jpaQuery.from(qMgComment).where(booleanExpression, qMgComment.status.eq(status)).list(qMgComment);
            for (MgComment mgComment : mgCommentList) {
                if (mgComment != null) {
                    decreaseCommentCountAndCardCount(mgComment.getContentsId(), mgComment.getCardId(), mgComment.getParentCommentId());
                }
            }
        } else if (Const.Status.PASS.equals(status)) {
             /*
             change commentReport status to DEL/HIDD
             */
            JPAUpdateClause qCommentReportUpdate = new JPAUpdateClause(entityManager, qCommentReport);
            qCommentReportUpdate.where(booleanExpression1);
            qCommentReportUpdate.where(qCommentReport.handleResult.isNull());
            qCommentReportUpdate.set(qCommentReport.handleResult, status).execute();
        } else if (Const.Status.ACTV.equals(status)) {
            /*
            we only change mgComment's status to ACTV
             */
            JPAUpdateClause mgCommentUpdate = new JPAUpdateClause(entityManager, qMgComment);
            mgCommentUpdate.where(booleanExpression);
            mgCommentUpdate.set(qMgComment.status, status).execute();
            //TODO incrouse comment count of redis
        }
    }

    @Override
    public CommentResult findReplyCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId, Long parentCommentId) {
        JPAQuery commentCountQuery = new JPAQuery(entityManager);
        QMgComment qMgComment = QMgComment.mgComment;

        commentCountQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId),
                qMgComment.cardId.eq(cardId),
                qMgComment.parentCommentId.eq(parentCommentId),
                qMgComment.commentId.loe(commentId));
        Integer rowNumber = ((Long) commentCountQuery.count()).intValue();

        Integer offset = rowNumber < 11 ? 0 : rowNumber - 10;
        Integer limit = rowNumber < 11 ? 9 + rowNumber : 20;

        JPAQuery commentListQuery = new JPAQuery(entityManager);
        commentListQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId),
                qMgComment.cardId.eq(cardId),
                qMgComment.parentCommentId.eq(parentCommentId))
                .limit(limit)
                .offset(offset);
        List<MgComment> list = commentListQuery.list(qMgComment);
        List<CommentResult> resultList = new ArrayList<CommentResult>();
        if (list != null) {
            for (MgComment mgComment : list) {
                CommentResult commentResult = new CommentResult();
                commentResult.setCommentId(mgComment.getCommentId());
                commentResult.setCommentText(mgComment.getText());
                commentResult.setStatus(mgComment.getStatus());
                resultList.add(commentResult);
            }
        }

        JPAQuery cardQuery = new JPAQuery(entityManager);
        QMgCard qMgCard = QMgCard.mgCard;
        cardQuery.from(qMgCard).where(
                qMgCard.cardId.eq(cardId));
        List<MgCard> cardList = cardQuery.list(qMgCard);

        CommentResult result = new CommentResult();
        result.setCommentResultList(resultList);

        if (cardList.size() > 0) {
            MgCard mgCard = cardList.get(0);
            result.setContentCardType(mgCard.getCardType());
            if (mgCard.getCardType().equals("LANDING")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            } else if (mgCard.getCardType().equals("PHOTO")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("PANORAMA")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            } else if (mgCard.getCardType().equals("INTR")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNSEMBED")) {
                //TODO
            } else if (mgCard.getCardType().equals("TEXT")) {
                result.setContentCardImage(mgCard.getDescription());
            } else if (mgCard.getCardType().equals("SNS_FB")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNS_IS")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNS_TB")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNS_TW")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("VIDEO")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            } else if (mgCard.getCardType().equals("YOUTUBE")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            }
        }
        return result;
    }

    @Override
    public CommentResult findReplyContentCommentsContextByCommentId(Long commentId, Long contentId, Long parentCommentId) {
        JPAQuery commentCountQuery = new JPAQuery(entityManager);
        QMgComment qMgComment = QMgComment.mgComment;

        commentCountQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId),
//                qMgComment.cardId.isNull(),
                qMgComment.parentCommentId.eq(parentCommentId),
                qMgComment.commentId.loe(commentId));
        Integer rowNumber = ((Long) commentCountQuery.count()).intValue();

        Integer offset = rowNumber < 11 ? 0 : rowNumber - 10;
        Integer limit = rowNumber < 11 ? 9 + rowNumber : 20;

        JPAQuery commentListQuery = new JPAQuery(entityManager);
        commentListQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId),
//                qMgComment.cardId.isNull(),
                qMgComment.parentCommentId.eq(parentCommentId))
                .limit(limit)
                .offset(offset);
        List<MgComment> list = commentListQuery.list(qMgComment);
        List<CommentResult> resultList = new ArrayList<CommentResult>();
        if (list != null) {
            for (MgComment mgComment : list) {
                CommentResult commentResult = new CommentResult();
                commentResult.setCommentId(mgComment.getCommentId());
                commentResult.setCommentText(mgComment.getText());
                commentResult.setStatus(mgComment.getStatus());
                resultList.add(commentResult);
            }
        }

        JPAQuery contentQuery = new JPAQuery(entityManager);
        QMgContents qMgContents = QMgContents.mgContents;
        contentQuery.from(qMgContents).where(
                qMgContents.contentsId.eq(contentId));
        List<MgContents> contentsList = contentQuery.list(qMgContents);

        CommentResult result = new CommentResult();
        result.setCommentResultList(resultList);

        if (contentsList.size() > 0) {
            MgContents mgContents = contentsList.get(0);
            result.setContentCardType(mgContents.getContentsType());
            result.setContentCardImage(mgContents.getThumbnailUrl());
        }
        return result;
    }

    @Override
    public CommentResult findCardCommentsContextByCommentId(Long commentId, Long contentId, Long cardId) {
        JPAQuery commentCountQuery = new JPAQuery(entityManager);
        QMgComment qMgComment = QMgComment.mgComment;

        commentCountQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId),
                qMgComment.cardId.eq(cardId),
                qMgComment.commentId.loe(commentId));
        Integer rowNumber = ((Long) commentCountQuery.count()).intValue();

        Integer offset = rowNumber < 11 ? 0 : rowNumber - 10;
        Integer limit = rowNumber < 11 ? 9 + rowNumber : 20;

        JPAQuery commentListQuery = new JPAQuery(entityManager);
        commentListQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId),
                qMgComment.cardId.eq(cardId))
                .limit(limit)
                .offset(offset);
        List<MgComment> list = commentListQuery.list(qMgComment);
        List<CommentResult> resultList = new ArrayList<CommentResult>();
        if (list != null) {
            for (MgComment mgComment : list) {
                CommentResult commentResult = new CommentResult();
                commentResult.setCommentId(mgComment.getCommentId());
                commentResult.setCommentText(mgComment.getText());
                commentResult.setStatus(mgComment.getStatus());
                resultList.add(commentResult);
            }
        }

        JPAQuery cardQuery = new JPAQuery(entityManager);
        QMgCard qMgCard = QMgCard.mgCard;
        cardQuery.from(qMgCard).where(
                qMgCard.cardId.eq(cardId));
        List<MgCard> cardList = cardQuery.list(qMgCard);

        CommentResult result = new CommentResult();
        result.setCommentResultList(resultList);

        if (cardList.size() > 0) {
            MgCard mgCard = cardList.get(0);
            result.setContentCardType(mgCard.getCardType());
            if (mgCard.getCardType().equals("LANDING")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            } else if (mgCard.getCardType().equals("PHOTO")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("PANORAMA")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            } else if (mgCard.getCardType().equals("INTR")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNSEMBED")) {
                //TODO
            } else if (mgCard.getCardType().equals("TEXT")) {
                result.setContentCardImage(mgCard.getDescription());
            } else if (mgCard.getCardType().equals("SNS_FB")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNS_IS")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNS_TB")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("SNS_TW")) {
                result.setContentCardImage(mgCard.getUrl());
            } else if (mgCard.getCardType().equals("VIDEO")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            } else if (mgCard.getCardType().equals("YOUTUBE")) {
                result.setContentCardImage(mgCard.getVideoThumbnailUrl());
            }
        }
        return result;
    }

    @Override
    public CommentResult findContentCommentsContextByCommentId(Long commentId, Long contentId) {
        JPAQuery commentCountQuery = new JPAQuery(entityManager);
        QMgComment qMgComment = QMgComment.mgComment;

        commentCountQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId),
//                qMgComment.cardId.isNull(),
                qMgComment.commentId.loe(commentId));
        Integer rowNumber = ((Long) commentCountQuery.count()).intValue();

        Integer offset = rowNumber < 11 ? 0 : rowNumber - 10;
        Integer limit = rowNumber < 11 ? 9 + rowNumber : 20;

        JPAQuery commentListQuery = new JPAQuery(entityManager);
        commentListQuery.from(qMgComment).where(
                qMgComment.contentsId.eq(contentId)
//                ,qMgComment.cardId.isNull()
        )
                .limit(limit)
                .offset(offset);
        List<MgComment> list = commentListQuery.list(qMgComment);
        List<CommentResult> resultList = new ArrayList<CommentResult>();
        if (list != null) {
            for (MgComment mgComment : list) {
                CommentResult commentResult = new CommentResult();
                commentResult.setCommentId(mgComment.getCommentId());
                commentResult.setCommentText(mgComment.getText());
                commentResult.setStatus(mgComment.getStatus());
                resultList.add(commentResult);
            }
        }

        JPAQuery contentQuery = new JPAQuery(entityManager);
        QMgContents qMgContents = QMgContents.mgContents;
        contentQuery.from(qMgContents).where(
                qMgContents.contentsId.eq(contentId));
        List<MgContents> contentsList = contentQuery.list(qMgContents);

        CommentResult result = new CommentResult();
        result.setCommentResultList(resultList);

        if (contentsList.size() > 0) {
            MgContents mgContents = contentsList.get(0);
            result.setContentCardType(mgContents.getContentsType());
            result.setContentCardImage(mgContents.getThumbnailUrl());
        }
        return result;
    }

    @Override
    public String clearList(String commentIdStrings) throws Exception {
        String [] commentIds = ArrayUtil.arrayUnique(commentIdStrings);
        StringBuilder sqlSb = new StringBuilder();
        String clearString = new String("");
        sqlSb.append("select group_concat(DISTINCT(mc.comment_id)) from MG_COMMENT mc where 1=1 ");
        //have no handle history
        sqlSb.append(" AND (((SELECT  count(*) count FROM COMMENT_HANDLE_HISTORY WHERE comment_id = mc.comment_id) = 0 )");
        //or have new report
        sqlSb.append(" OR ((SELECT  count(*) count FROM COMMENT_REPORT WHERE comment_id = mc.comment_id AND handle_result IS NULL) > 0 ))");
        for (int i = 0; i < commentIds.length; i++){
            if (i == 0) {
                sqlSb.append(" AND (comment_id=");
                sqlSb.append(commentIds[i]);
            }  else {
                sqlSb.append(" OR comment_id=");
                sqlSb.append(commentIds[i]);
            }
            if (i == commentIds.length - 1) {
                 sqlSb.append(")");
            }
        }
        Query query = entityManager.createNativeQuery(sqlSb.toString());
        List objecList = query.getResultList();
        if (objecList == null) {
            throw new BadRequestException();
        }
        if (objecList.size() > 0) {
            if(objecList.get(0)!=null) {
                clearString = objecList.get(0).toString();
            }
        }
        return clearString;
    }
}
