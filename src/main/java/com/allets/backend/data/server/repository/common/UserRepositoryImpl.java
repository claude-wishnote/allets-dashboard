package com.allets.backend.data.server.repository.common;


import com.allets.backend.data.server.elasticsearch.ElasticSearchTemplate;
import com.allets.backend.data.server.data.result.AlertResult;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.entity.common.QFollow;
import com.allets.backend.data.server.entity.common.QUserReport;
import com.allets.backend.data.server.exception.BadRequestException;
import com.allets.backend.data.server.utils.JsonUtil;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.SpecialUser;
import com.allets.backend.data.server.entity.common.QUser;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
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
import java.util.HashMap;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private static Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

    @Autowired
    private UserHandleHistoryRepository userHandleHistoryRepository;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    ElasticSearchTemplate elasticSearchTemplate;

    @Override
    public void updateUserReportHandleResults(Long uid, String action) throws Exception {
        QUserReport qUserReport = QUserReport.userReport;
        JPAUpdateClause qUserReportUpdate = new JPAUpdateClause(entityManager, qUserReport);
        qUserReportUpdate
                .where(qUserReport.reportedUid.eq(uid))
                .where(qUserReport.handleResult.isNull())
                .set(qUserReport.handleResult, action).execute();

    }

    @Override
    public Page<UserResult> findUsers(Integer offset, Integer limit, String q) throws Exception {
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

        String listSql = "SELECT " +
                " uid," +
                " name," +
                " sex," +
                " birthday," +
                " cdate," +
                " email," +
                " photo," +
                " level," +
                " (SELECT count(aid) FROM ALBUM_BOOKMARK WHERE aid=ur.uid) bookMarkCount," +
                " (SELECT count(follower_uid) FROM FOLLOW WHERE follower_uid=ur.uid and ((select fur.level from USER fur where fur.uid =follow_uid)!= 'NORMAL')) subscribeEditorCount," +
                " (SELECT count(follow_uid) FROM FOLLOW WHERE follow_uid=ur.uid) subscriberCount," +
                " (SELECT count(comment_id) FROM MG_COMMENT mc WHERE mc.uid=ur.uid AND mc.status='DEL') deleteCommentsCount," +
                " (SELECT invaild_from FROM USER_INVALID ui WHERE ui.uid=ur.uid) invalidFrom," +
                " (SELECT invaild_to FROM USER_INVALID ui WHERE ui.uid=ur.uid) invalidTo," +
                " (SELECT id FROM USER_BLACK_LIST ubl WHERE ubl.uid=ur.uid) blackist," +
                "  status," +
                " (SELECT count(*) FROM USER_REPORT urp WHERE urp.reported_uid=ur.uid) userReportedCount," +
                " intro_message," +
                " age15plus," +
                " (SELECT previous_status FROM USER_INVALID ui WHERE ui.uid=ur.uid) previousStatus," +
                " (SELECT count(follower_uid) FROM FOLLOW WHERE follower_uid=ur.uid) subscribeCount" +
                " FROM USER ur ";
        if (q != null && !q.isEmpty()) {
            listSql = listSql + " WHERE ";
            for (int i = 0; i < searchBy.size(); i++) {
                if ("nickName".equals(searchBy.get(i))) {
                    listSql = listSql + " ur.name LIKE '%" + keyword.get(i) + "%' ";
                } else if ("email".equals(searchBy.get(i))) {
                    listSql = listSql + " ur.email LIKE '%" + keyword.get(i) + "%' ";
                } else if ("uid".equals(searchBy.get(i))) {
                    listSql = listSql + " ur.uid = " + keyword.get(i);
                }
            }
        }
        listSql = listSql +
                " LIMIT 15 " +
                " OFFSET 0";
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();
        List<UserResult> userResultList = new ArrayList<UserResult>();

        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserResult userResult = new UserResult();
            if (obj[0] != null) {
                userResult.setUid(Long.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                userResult.setName(obj[1].toString());
            }
            if (obj[2] != null) {
                userResult.setSex(obj[2].toString());
            }
            if (obj[3] != null) {
                userResult.setBirthday(obj[3].toString());
            }
            if (obj[4] != null) {
                userResult.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[4].toString()));
            }
            if (obj[5] != null) {
                userResult.setEmail(obj[5].toString());
            }
            if (obj[6] != null) {
                userResult.setPhoto(obj[6].toString());
            }
            if (obj[7] != null) {
                userResult.setLevel(obj[7].toString());
            }
            if (obj[8] != null) {
                userResult.setBookMarkCount(Integer.valueOf(obj[8].toString()));
            }
            if (obj[9] != null) {
                userResult.setSubscribeEditorCount(Integer.valueOf(obj[9].toString()));
            }
            if (obj[10] != null) {
                userResult.setSubscriberCount(Integer.valueOf(obj[10].toString()));
            }
            if (obj[11] != null) {
                userResult.setDeleteCommentsCount(Integer.valueOf(obj[11].toString()));
            }
            if (obj[12] != null) {
                userResult.setInvalidFrom(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[12].toString()));
            }
            if (obj[13] != null) {
                userResult.setInvalidTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[13].toString()));
            }
            if (obj[14] != null) {
                userResult.setBlackList(true);
            } else {
                userResult.setBlackList(false);
            }
            if (obj[15] != null) {
                userResult.setStatus(obj[15].toString());
            }
            if (obj[16] != null) {
                userResult.setUserReportedCount(Integer.valueOf(obj[16].toString()));
            }
            if (obj[17] != null) {
                userResult.setIntroMessage(obj[17].toString());
            }
            if (obj[18] != null) {
                userResult.setAge15plus(Integer.valueOf(obj[18].toString()));
            }
            if (obj[19] != null) {
                userResult.setPreviousStatus(obj[19].toString());
            }
            if (obj[20] != null) {
                userResult.setSubscribeCount(Integer.valueOf(obj[20].toString()));
            }
            userResultList.add(userResult);
        }

        Pageable pageable = new PageRequest(offset / limit, limit);
        PageImpl<UserResult> page = new PageImpl<UserResult>(userResultList, pageable, userResultList.size());
        return page;
    }

    @Override
    public UserDTO updateUserProfiles(UserResult userResult, Long monitorId, String q, String defaultName) throws Exception {
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
                        if (arr.length > 1) {
                            keyword.add(arr[1]);
                        } else {
                            keyword.add(null);
                        }
                    }
                }
            }
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(userResult.getUid());
        userDTO.setPhoto(userResult.getPhoto());
        userDTO.setName(userResult.getName());
        userDTO.setIntroMessage(userResult.getIntroMessage());
        userDTO.setSubscriberCount(userResult.getSubscriberCount());
        userDTO.setSubscribeEditorCount(userResult.getSubscribeEditorCount());
        userDTO.setSubscribeCount(userResult.getSubscribeCount());
        userDTO.setSex(userResult.getSex());
        userDTO.setEmail(userResult.getEmail());
        userDTO.setBirthday(userResult.getBirthday());
        userDTO.setPreviousStatus(userResult.getPreviousStatus());
        QUser qUser = QUser.user;
        JPAUpdateClause qUserUpdate = new JPAUpdateClause(entityManager, qUser);
        qUserUpdate.where(qUser.uid.eq(userResult.getUid()));
        if (q != null && !q.isEmpty()) {
            boolean boolUpdate = false;
            for (int i = 0; i < searchBy.size(); i++) {
                if ("nickName".equals(searchBy.get(i))) {
                    qUserUpdate.set(qUser.name, defaultName);
                    userDTO.setName(defaultName);
                    boolUpdate = true;
                } else if ("introMessage".equals(searchBy.get(i))) {
                    qUserUpdate.set(qUser.introMessage, "");
                    userDTO.setIntroMessage("");
                    boolUpdate = true;
                } else if ("photo".equals(searchBy.get(i))) {
                    qUserUpdate.set(qUser.photo, "A00/default_avatar.png");
                    userDTO.setPhoto("A00/default_avatar.png");
                    boolUpdate = true;
                } else if ("email".equals(searchBy.get(i))) {
                    qUserUpdate.set(qUser.email, keyword.get(i));
                    userDTO.setEmail(keyword.get(i));
                    boolUpdate = true;
                } else if ("birthday".equals(searchBy.get(i))) {
                    qUserUpdate.set(qUser.birthday, keyword.get(i));
                    userDTO.setBirthday(keyword.get(i));
                    boolUpdate = true;
                } else if ("sex".equals(searchBy.get(i))) {
                    qUserUpdate.set(qUser.sex, keyword.get(i));
                    userDTO.setSex(keyword.get(i));
                    boolUpdate = true;
                }
            }
            if (boolUpdate) {
                qUserUpdate.execute();
            }
        }
        if (q != null && !q.isEmpty()) {
            for (int i = 0; i < searchBy.size(); i++) {
                if ("subscribe".equals(searchBy.get(i))) {
                    QFollow qFollow = QFollow.follow;
                    JPADeleteClause qFollowDelete = new JPADeleteClause(entityManager, qFollow);
                    qFollowDelete
                            .where(qFollow.followerUid.eq(userResult.getUid()))
                            .execute();
                    userDTO.setSubscribeCount(0);
                    userDTO.setSubscribeEditorCount(0);
                }
                if ("subscriber".equals(searchBy.get(i))) {
                    QFollow qFollow = QFollow.follow;
                    JPADeleteClause qFollowDelete = new JPADeleteClause(entityManager, qFollow);
                    qFollowDelete
                            .where(qFollow.followUid.eq(userResult.getUid()))
                            .execute();
                    userDTO.setSubscriberCount(0);
                }
            }
        }
        return userDTO;
    }

    @Override
    public Page<UserResult> findUsersSimple(Integer offset, Integer limit, String q) throws Exception {
        HashMap<String, Object> parametersMap = new HashMap<>();
//        List<String> searchBy = new ArrayList<String>();
//        List<String> keyword = new ArrayList<String>();
        if ((q != null && !q.isEmpty())) {
            if (StringUtils.isNotBlank(q)) {
                String dq = URLDecoder.decode(q, "utf-8");
                String[] conditions = dq.split(",");
                if (conditions != null & conditions.length > 0) {
                    for (String c : conditions) {
                        String[] arr = c.split("=");
                        parametersMap.put(arr[0], arr[1]);
//                        searchBy.add(arr[0]);
//                        keyword.add(arr[1]);
                    }
                }
            }
        }
        List<UserResult> userResultList = new ArrayList<UserResult>();
        if (!parametersMap.containsKey("uid") && parametersMap.containsKey("searchSource") && ((String) parametersMap.get("searchSource")).equals("els") && elasticSearchTemplate.getElasticsearchSwitch()) {
            String field = "name";
            String value = "";
            if (parametersMap.containsKey("nickName")) {
                field = "name";
                value = (String) parametersMap.get("nickName");
            } else if (parametersMap.containsKey("email")) {
                field = "email";
                value = (String) parametersMap.get("email");
            }
            SearchHits hits = elasticSearchTemplate.searcherUser("user_index", "USER", field, value, null, 0, 20);
            for (SearchHit hit : hits.getHits()) {
                UserResult userResult = new UserResult();
                if (hit.getSource() != null) {
                    if (hit.getSource().get("uid") != null) {
                        userResult.setUid(Long.valueOf(hit.getSource().get("uid").toString()));
                    }
                    if (hit.getSource().get("name") != null) {
                        userResult.setName(hit.getSource().get("name").toString());
                    }
                    if (hit.getSource().get("email") != null) {
                        userResult.setEmail(hit.getSource().get("email").toString());
                    }
                }
                userResultList.add(userResult);
            }
        } else {
            StringBuilder listSqlSB = new StringBuilder();
            listSqlSB.append("SELECT ");
            listSqlSB.append(" name, ");
            listSqlSB.append(" email, ");
            listSqlSB.append(" uid ");
            listSqlSB.append(" FROM USER ur ");

            if (parametersMap.containsKey("nickName")) {
                listSqlSB.append(" where ");
                listSqlSB.append(" ur.name ='");
                listSqlSB.append(parametersMap.get("nickName"));
                listSqlSB.append("' ");
            } else if (parametersMap.containsKey("email")) {
                listSqlSB.append(" where ");
                listSqlSB.append(" ur.email = '");
                listSqlSB.append(parametersMap.get("email"));
                listSqlSB.append("' ");
            } else if (parametersMap.containsKey("uid")) {
                listSqlSB.append(" where ");
                listSqlSB.append(" ur.uid = ");
                listSqlSB.append(parametersMap.get("uid"));
            }
            listSqlSB.append(" LIMIT 20 ");
            listSqlSB.append(" OFFSET 0 ");
            Query listQuery = entityManager.createNativeQuery(listSqlSB.toString());

            List objecArraytList = listQuery.getResultList();
            userResultList = new ArrayList<UserResult>();

            for (int i = 0; i < objecArraytList.size(); i++) {
                Object[] obj = (Object[]) objecArraytList.get(i);
                UserResult userResult = new UserResult();
                if (obj[0] != null) {
                    userResult.setName(obj[0].toString());
                }
                if (obj[1] != null) {
                    userResult.setEmail(obj[1].toString());
                }
                if (obj[2] != null) {
                    userResult.setUid(Long.valueOf(obj[2].toString()));
                }
                userResultList.add(userResult);
            }
        }

        Pageable pageable = new PageRequest(offset / limit, limit);
        PageImpl<UserResult> page = new PageImpl<UserResult>(userResultList, pageable, userResultList.size());
        return page;
    }

    @Override
    public Integer findTotalReportCount(Long uid) throws Exception {
        String listSql = "SELECT " +
                " (SELECT  COUNT(*) FROM USER_REPORT WHERE reported_uid = " +
                uid +
                ")" +
                "  + COUNT(cr.comment_id) totalReportCoount" +
                " FROM COMMENT_REPORT cr" +
                " LEFT JOIN MG_COMMENT mg ON mg.uid = " +
                uid +
                " WHERE" +
                " cr.comment_id = mg.comment_id";

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
    public Integer findShareCount(Long uid) throws Exception {
        if (uid == null) {
            logger.error("uid or is null");
            return null;
        }

        String hashKeyRightSide = ":" + (uid / 1000);
        String shareCount = null;

        String restKey = "CONTENTS_SHARE_COUNT" + hashKeyRightSide;
        String hashKey = uid.toString();

        if (hashOperations.hasKey(restKey, hashKey)) {
            shareCount = (String) hashOperations.get(restKey, hashKey);
        } else {
            hashOperations.put(restKey, hashKey, String.valueOf(0));
        }

        if (shareCount == null)
            shareCount = "0";

        return Integer.valueOf(shareCount);
    }

    @Override
    public Integer findAlertCount(Long uid) throws Exception {
        if (uid == null) {
            logger.error("uid or is null");
            return null;
        }
        String hashKeyRightSide = ":" + (uid / 10000);

        String alertInfo = null;
        Integer alertCount = 0;
        String restKey = "ACCOUNT_ALERT" + hashKeyRightSide;
        String hashKey = uid.toString();

        if (hashOperations.hasKey(restKey, hashKey)) {
            alertInfo = (String) hashOperations.get(restKey, hashKey);
        } else {
            AlertResult alert = new AlertResult();
            alert.setAlert_count(0);
            alert.setAlert_queue(new ArrayList<String>());
            hashOperations.put(restKey, hashKey, JsonUtil.marshallingJson(alert));
        }


        if (alertInfo != null)
            alertCount = JsonUtil.unmarshallingJson(alertInfo, AlertResult.class).getAlert_count();
        return alertCount;
    }

    @Override
    public AlertResult updateAlertCount(Long uid, String alertType) throws Exception {
        if (uid == null) {
            logger.error("uid or is null");
            return null;
        }
        String hashKeyRightSide = ":" + (uid / 10000);

        String restKey = "ACCOUNT_ALERT" + hashKeyRightSide;
        String hashKey = uid.toString();

        String alertInfo = null;
        Integer alertCount = 0;
        AlertResult alert = null;

        if (hashOperations.hasKey(restKey, hashKey)) {
            alertInfo = (String) hashOperations.get(restKey, hashKey);
            alert = JsonUtil.unmarshallingJson(alertInfo, AlertResult.class);
            alert.setAlert_count(alert.getAlert_count() + 1);
            List<String> list = alert.getAlert_queue();
            if (!alertType.equals("") && !list.contains(alertType)) {
                list.add(alertType);
            }
            alert.setAlert_queue(list);
            hashOperations.put(restKey, hashKey, JsonUtil.marshallingJson(alert));
        } else {
            alert = new AlertResult();
            alert.setAlert_count(1);
            List<String> list = new ArrayList<String>();
            list.add(alertType);
            alert.setAlert_queue(list);
            if (!alertType.equals("")) {
                list.add(alertType);
            }
            hashOperations.put(restKey, hashKey, JsonUtil.marshallingJson(alert));
        }
        return alert;
    }

    @Override
    public Page<UserResult> findHandledUsers(Integer offset, Integer limit, String q, String sort) throws Exception {
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

        String countSql = "SELECT  " +
                "  count(id) " +
                "  FROM " +
                "  USER_HANDLE_HISTORY uhh " +
                "  LEFT JOIN USER u on u.uid = uhh.uid" +
                "  WHERE 1 = 1 ";


        if (parametersMap != null) {
            if (parametersMap.containsKey("email")) {
                countSql = countSql + " AND u.email LIKE '%" + parametersMap.get("email") + "%' ";
            } else if (parametersMap.containsKey("nickName")) {
                countSql = countSql + " AND u.name LIKE '%" + parametersMap.get("nickName") + "%' ";
            } else if (parametersMap.containsKey("keyword")) {
                countSql = countSql + " AND (u.email LIKE '%" + parametersMap.get("keyword") + "%' " +
                        "OR u.name LIKE '%" + parametersMap.get("keyword") + "%') ";
            }
            if (parametersMap.containsKey("handleResult") && !parametersMap.get("handleResult").equals("ALL")) {
                countSql = countSql + " AND uhh.handle_result ='" + parametersMap.get("handleResult") + "' ";
            }
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                countSql = countSql + " AND uhh.cdate BETWEEN '" + parametersMap.get("startDate") + "' AND '" + parametersMap.get("endDate") + "' ";
            } else if (parametersMap.containsKey("startDate")) {
                countSql = countSql + " AND uhh.cdate >= '" + parametersMap.get("startDate") + "' ";
            } else if (parametersMap.containsKey("endDate")) {
                countSql = countSql + " AND uhh.cdate <= '" + parametersMap.get("endDate") + "' ";
            }
        }

        Query countQuery = entityManager.createNativeQuery(countSql);
        List objecCountList = countQuery.getResultList();
        if (objecCountList == null) {
            throw new BadRequestException();
        }
        int count = 0;
        if (objecCountList.size() > 0) {
            count = Integer.valueOf(objecCountList.get(0).toString());
        }


        List<String> orderBy = new ArrayList<String>();
        if ((sort != null && !sort.isEmpty())) {
            if (StringUtils.isNotBlank(sort)) {
                String[] conditions = sort.split(",");
                if (conditions != null & conditions.length > 0) {
                    for (String c : conditions) {
                        orderBy.add(c);
                    }
                }
            }
        }

        String listSql = "SELECT  " +
                "   uhh.uid, " +
                "  (SELECT name FROM USER WHERE uid = uhh.uid) nickName, " +
                "  (SELECT COUNT(follower_uid) FROM FOLLOW WHERE follower_uid = uhh.uid) subscriberCount, " +
                "  (SELECT COUNT(*) FROM USER_REPORT WHERE reported_uid = uhh.uid) userReportedCount, " +
                "  (SELECT GROUP_CONCAT(DISTINCT (report_type)) FROM USER_REPORT WHERE reported_uid = uhh.uid AND report_type IS NOT NULL) report_type," +
                "  uhh.cdate, " +
                "  (SELECT invaild_to FROM USER_INVALID WHERE uid = uhh.uid) invalidTo, " +
                "  (SELECT name FROM MONITOR WHERE monitor_id = uhh.monitor_id) monitorName, " +
                "  handle_result ," +
                "  id " +
                "  FROM " +
                "  USER_HANDLE_HISTORY uhh " +
                "  LEFT JOIN USER u on u.uid = uhh.uid" +
                "  WHERE 1 = 1 ";


        if (parametersMap != null) {
            if (parametersMap.containsKey("email")) {
                listSql = listSql + " AND u.email LIKE '%" + parametersMap.get("email") + "%' ";
            } else if (parametersMap.containsKey("nickName")) {
                listSql = listSql + " AND u.name LIKE '%" + parametersMap.get("nickName") + "%' ";
            } else if (parametersMap.containsKey("keyword")) {
                listSql = listSql + " AND (u.email LIKE '%" + parametersMap.get("keyword") + "%'" +
                        " OR u.name LIKE '%" + parametersMap.get("keyword") + "%') ";
            }
            if (parametersMap.containsKey("handleResult") && !parametersMap.get("handleResult").equals("ALL")) {
                listSql = listSql + " AND uhh.handle_result ='" + parametersMap.get("handleResult") + "' ";
            }
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                listSql = listSql + " AND uhh.cdate BETWEEN '" + parametersMap.get("startDate") + "' AND '" + parametersMap.get("endDate") + "' ";
            } else if (parametersMap.containsKey("startDate")) {
                listSql = listSql + " AND uhh.cdate >= '" + parametersMap.get("startDate") + "' ";
            } else if (parametersMap.containsKey("endDate")) {
                listSql = listSql + " AND uhh.cdate <= '" + parametersMap.get("endDate") + "' ";
            }
        }


        listSql = listSql +
                " order by id DESC";
        if (orderBy.size() > 0) {
            for (int i = 0; i < orderBy.size(); i++) {
                String string = orderBy.get(i);
                if (string != null) {
                    String seq = string.substring(0, 1);
                    String orderString = string.substring(1, string.length());
                    if (orderString.equals("monitorId")) {
                        if (seq.equals("-")) {
                            listSql = listSql + ",monitor_id DESC ";
                        } else if (seq.equals("+")) {
                            listSql = listSql + ",monitor_id ASC ";
                        }
                    }
                }
            }
        }
        listSql = listSql +
                " LIMIT :limit OFFSET :offset ";

        Query listQuery = entityManager.createNativeQuery(listSql);
        listQuery.setParameter("limit", limit);
        listQuery.setParameter("offset", offset);

        List objecArraytList = listQuery.getResultList();
        List<UserResult> userResultList = new ArrayList<UserResult>();

        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserResult userResult = new UserResult();
            if (obj[0] != null) {
                userResult.setUid(Long.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                userResult.setName(obj[1].toString());
            }
            if (obj[2] != null) {
                userResult.setSubscriberCount(Integer.valueOf(obj[2].toString()));
            }
            if (obj[3] != null) {
                userResult.setUserReportedCount(Integer.valueOf(obj[3].toString()));
            }
            if (obj[4] != null) {
                userResult.setReportType(obj[4].toString());
            }
            if (obj[5] != null) {
                userResult.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[5].toString()));
            }
            if (obj[6] != null) {
                userResult.setInvalidTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[6].toString()));
            }
            if (obj[7] != null) {
                userResult.setMonitorName(obj[7].toString());
            }
            if (obj[8] != null) {
                userResult.setHandleResult(obj[8].toString());
            }

            userResultList.add(userResult);
        }

        Pageable pageable = new PageRequest(offset / limit, limit);
        PageImpl<UserResult> page = new PageImpl<UserResult>(userResultList, pageable, count);
        return page;
    }

    @Override
    public List<SpecialUser> findSpecialUsers() throws Exception {
        String sql = " SELECT " +
                " ci.uid, " +
                " ci.interactive_photo, " +
                " ci.interactive_text, " +
                " u.name " +
                " from COMMENT_INTERACTIVE ci " +
                " LEFT JOIN USER u ON u.uid=ci.uid ";

        Query specialUserQuery = entityManager.createNativeQuery(sql);
        List objecArraytList = specialUserQuery.getResultList();
        List<SpecialUser> specialUserList = new ArrayList<SpecialUser>();

        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            SpecialUser specialUser = new SpecialUser();
            if (obj[0] != null) {
                specialUser.setUid(Long.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                specialUser.setPhoto(obj[1].toString());
            }
            if (obj[2] != null) {
                specialUser.setText(obj[2].toString());
            }
            if (obj[3] != null) {
                specialUser.setName(obj[3].toString());
            }
            specialUserList.add(specialUser);
        }
        return specialUserList;
    }

    @Override
    public void deleteAlertQueue(Long uid, String alertType) throws Exception {
        if (uid == null) {
            logger.error("uid or is null");
            return;
        }
        String hashKeyRightSide = ":" + (uid / 10000);

        String restKey = "ACCOUNT_ALERT" + hashKeyRightSide;
        String hashKey = uid.toString();

        String alertInfo = null;
        Integer alertCount = 0;
        AlertResult alert = null;

        if (hashOperations.hasKey(restKey, hashKey)) {
            alertInfo = (String) hashOperations.get(restKey, hashKey);
            alert = JsonUtil.unmarshallingJson(alertInfo, AlertResult.class);
            List<String> list = alert.getAlert_queue();
            if (!alertType.equals("")) {
                if (list.contains(alertType)) {
                    if (alert.getAlert_count() > 0) {
                        alert.setAlert_count(alert.getAlert_count() - 1);
                    }
                    list.remove(alertType);
                    alert.setAlert_queue(list);
                    hashOperations.put(restKey, hashKey, JsonUtil.marshallingJson(alert));
                }
            }
        }
    }

    @Override
    public String selectUserLoginType(Long uid) throws Exception {
        StringBuilder loginTypeString = new StringBuilder();

        StringBuilder sqlsbE = new StringBuilder();
        sqlsbE.append("SELECT password FROM USER ");
        sqlsbE.append("WHERE uid=");
        sqlsbE.append(uid);
        Query ElistQuery = entityManager.createNativeQuery(sqlsbE.toString());
        List EobjecArraytList = ElistQuery.getResultList();
        if (EobjecArraytList.size() > 0) {
            if (EobjecArraytList.get(0) != null) {
                loginTypeString.append("E,");
            }
        }
        sqlsbE.append("SELECT  sns_type FROM USER_SNS ");
        StringBuilder sqlsbKF = new StringBuilder();
        sqlsbKF.append("SELECT  sns_type FROM USER_SNS ");
        sqlsbKF.append("WHERE uid=");
        sqlsbKF.append(uid);
        sqlsbKF.append(" AND sns_type IS NOT NULL GROUP BY sns_type");

        Query KFlistQuery = entityManager.createNativeQuery(sqlsbKF.toString());
        List KFobjecArraytList = KFlistQuery.getResultList();
        if (KFobjecArraytList == null) {
            throw new BadRequestException();
        }
        if (KFobjecArraytList.size() > 0) {
            for (int i = 0; i < KFobjecArraytList.size(); i++) {
                String obj = (String) KFobjecArraytList.get(i);
                loginTypeString.append(obj);
                if (i > 0) {
                    loginTypeString.append(",");
                }
            }
        }
        return loginTypeString.toString();
    }

    @Override
    public Integer updateUserPassword(Long uid, String password) throws Exception {
        QUser qUser = QUser.user;
        JPAUpdateClause QUserUpdate = new JPAUpdateClause(entityManager, qUser);

        Integer num = Long.valueOf(QUserUpdate
                .where(qUser.uid.eq(uid))
                .set(qUser.password, password).execute()).intValue();

        return num;
    }
}
