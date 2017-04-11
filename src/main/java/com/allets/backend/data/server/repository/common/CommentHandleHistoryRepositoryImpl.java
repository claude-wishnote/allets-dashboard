package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.QSlangWord;
import com.allets.backend.data.server.entity.common.SlangWord;
import com.mysema.query.jpa.impl.JPAQuery;
import com.allets.backend.data.server.data.result.CommentResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by claude on 2015/8/29.
 */
public class CommentHandleHistoryRepositoryImpl implements CommentHandleHistoryRepositoryCustom {

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;
    @Autowired
    CommentRepository commentRepository;
    public List<SlangWord> list;

    @Override
    public Page<CommentResult> findAllHandledComment(Integer offset, Integer limit, String q) throws Exception {
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

        String countSql = "SELECT " +
                " COUNT(chh.comment_id)" +
                " FROM COMMENT_HANDLE_HISTORY  chh  " +
                " LEFT JOIN  MG_COMMENT mc ON mc.comment_id = chh.comment_id " +
                " LEFT JOIN  MG_CARD mcd ON mcd.card_id = mc.card_id " +
                " LEFT JOIN  USER u ON u.uid = mc.uid " +
                "  WHERE 1 = 1 ";

        if (parametersMap != null) {
            if (parametersMap.containsKey("email")) {
                countSql = countSql + " AND u.email LIKE '%" + parametersMap.get("email") + "%' ";
            } else if (parametersMap.containsKey("nickName")) {
                countSql = countSql + " AND u.name LIKE '%" + parametersMap.get("nickName") + "%' ";
            } else if (parametersMap.containsKey("keyword")) {
                countSql = countSql + " AND mc.text LIKE '%" + parametersMap.get("keyword") + "%' ";
            } else if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    countSql = countSql +
                            " AND (chh.cdate BETWEEN '" + startDate + "'" +
                            " AND  '" + endDate + "')";
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    countSql = countSql + " AND chh.cdate >= '" + startDate + "' ";
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    countSql = countSql + " AND chh.cdate <= '" + endDate + "' ";
                }
            }

            if (parametersMap.containsKey("handleResult") && !parametersMap.get("handleResult").equals("ALL")) {
                countSql = countSql + " AND chh.handle_result ='" + parametersMap.get("handleResult") + "' ";
            }
        }

        Query countQuery = entityManager.createNativeQuery(countSql);
        List countArraytList = countQuery.getResultList();
        Long count = Long.valueOf(countArraytList.get(0).toString());


        String listSql = "SELECT " +
                "chh.comment_id," +
                "(SELECT  COUNT(*) FROM COMMENT_REPORT cc WHERE cc.comment_id= chh.comment_id) reportCount, " +
                "(SELECT  CASE WHEN cl.parent_comment_id IS NULL THEN 'C' ELSE 'RC' END AS 'Comment Type'  FROM MG_COMMENT cl WHERE cl.comment_id = chh.comment_id) 'Comment Type'," +
                "chh.cdate," +
                "u.uid," +
                "u.name," +
                "mc.text," +
                "(select group_concat(DISTINCT(report_type)) from COMMENT_REPORT where comment_id=chh.comment_id and report_type IS NOT NULL) report_type, " +
                "chh.handle_result, " +
                "mc.contents_id," +
                "mc.card_id," +
                "mcd.ordering," +
                "(SELECT  COUNT(*) FROM  MG_COMMENT_LIKE cl WHERE cl.comment_id = chh.comment_id) likeCount," +
                " (SELECT COUNT(*) FROM MG_COMMENT cc WHERE cc.parent_comment_id = chh.comment_id AND cc.status NOT IN ('HOLD' , 'DEL', 'HIDD')) childCommentCount," +
                "chh.monitor_id," +
                "(SELECT  m.name FROM  MONITOR m WHERE m.monitor_id = chh.monitor_id) monitorName, " +
                "mc.parent_comment_id" +
                " FROM COMMENT_HANDLE_HISTORY  chh  " +
                " LEFT JOIN  MG_COMMENT mc ON mc.comment_id = chh.comment_id " +
                " LEFT JOIN  MG_CARD mcd ON mcd.card_id = mc.card_id " +
                " LEFT JOIN  USER u ON u.uid = mc.uid " +
                "  WHERE 1 = 1 ";
        if (parametersMap != null) {
            if (parametersMap.containsKey("email")) {
                listSql = listSql + " AND u.email LIKE '%" + parametersMap.get("email") + "%' ";
            } else if (parametersMap.containsKey("nickName")) {
                listSql = listSql + " AND u.name LIKE '%" + parametersMap.get("nickName") + "%' ";
            } else if (parametersMap.containsKey("keyword")) {
                listSql = listSql + " AND mc.text LIKE '%" + parametersMap.get("keyword") + "%' ";
            } else if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSql = listSql +
                            " AND (chh.cdate BETWEEN '" + startDate + "'" +
                            " AND  '" + endDate + "')";
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSql = listSql + " AND chh.cdate >= '" + startDate + "' ";
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSql = listSql + " AND chh.cdate <= '" + endDate + "' ";
                }
            }

            if (parametersMap.containsKey("handleResult") && !parametersMap.get("handleResult").equals("ALL")) {
                listSql = listSql + " AND chh.handle_result ='" + parametersMap.get("handleResult") + "' ";
            }

        }

        listSql = listSql + " ORDER BY chh.cdate DESC";
        listSql = listSql + " LIMIT " + limit +
                " OFFSET " + offset;
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();
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
                commentResult.setHandleResult(obj[8].toString());
            }
            if (obj[9] != null) {
                commentResult.setContentId(Long.valueOf(obj[9].toString()));
            }
            if (obj[10] != null) {
                commentResult.setCardId(Long.valueOf(obj[10].toString()));
                if (obj[11] != null) {
                    commentResult.setCardOrdering(Integer.parseInt(obj[11].toString()));
                } else {
                    commentResult.setCardOrdering(0);
                }
            }
            commentResult.setLikeCount(Integer.valueOf(obj[12].toString()));
            if (obj[13] != null) {
                commentResult.setRecommentCount(Integer.valueOf(obj[13].toString()));
            }
            if (obj[14] != null) {
                commentResult.setMonitorId(Long.valueOf(obj[14].toString()));
            }
            if (obj[15] != null) {
                commentResult.setMonitorName(obj[15].toString());
            }
            if (obj[16] != null) {
                commentResult.setParentCommentId(Long.valueOf(obj[16].toString()));
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

    public String findSlangType(String commentText) throws Exception {

        String type = "-";
        if(commentText==null)
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

}
