package com.allets.backend.data.server.repository.common;


import com.allets.backend.data.server.data.result.UserStatisticsResult;
import com.allets.backend.data.server.elasticsearch.ElasticSearchTemplate;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.exception.BadRequestException;
import com.allets.backend.data.server.utils.JsonUtil;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.SpecialUser;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private static Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    ElasticSearchTemplate elasticSearchTemplate;

    @Override
    public List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception
    {
        HashMap<String,String> hashMap = new HashMap<>();
        if ((q != null && !q.isEmpty())) {
            if (StringUtils.isNotBlank(q)) {
                String dq = URLDecoder.decode(q, "utf-8");
                String[] conditions = dq.split(",");
                if (conditions != null & conditions.length > 0) {
                    for (String c : conditions) {
                        String[] arr = c.split("=");
                        hashMap.put(arr[0],arr[1]);
                    }
                }
            }
        }
        if(hashMap.get("stype").equals("gender"))
        {
            return findSatisticsResultByGender();
        }
        else if(hashMap.get("stype").equals("age"))
        {
            return findSatisticsResultByAge();
        }
        else if(hashMap.get("stype").equals("date"))
        {
            System.err.println(Boolean.valueOf(hashMap.get("isRegBySns")));
            return findSatisticsResultByDate(Boolean.valueOf(hashMap.get("isRegBySns")));
        }
        return null;
    }

    public List<UserStatisticsResult> findSatisticsResultByGender()
    {
        String listSql = "select count(uid),sex from USER group by sex";
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();
        List<UserStatisticsResult> list = new ArrayList<>();

        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserStatisticsResult userStatisticsResult = new UserStatisticsResult();
            if (obj[0] != null) {
                userStatisticsResult.setResult(Integer.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                userStatisticsResult.setType(obj[1].toString());
            }else if (obj[0] != null&&obj[1] == null)
            {
                userStatisticsResult.setType("N");
            }
            list.add(userStatisticsResult);
        }

        return list;
    }

    public List<UserStatisticsResult> findSatisticsResultByAge()
    {
        String listSql = "SELECT count(*) AS num, ((YEAR(CURDATE())-YEAR(birthday)) DIV 10 +1)*10 AS ageRange FROM allets_common.USER GROUP BY ageRange";
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();
        List<UserStatisticsResult> list = new ArrayList<>();

        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserStatisticsResult userStatisticsResult = new UserStatisticsResult();
            if (obj[0] != null) {
                userStatisticsResult.setResult(Integer.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                userStatisticsResult.setType(obj[1].toString()+"s");
            }
            else if (obj[0] != null&&obj[1] == null)
            {
                userStatisticsResult.setType("s");
            }
//            if (obj[4] != null) {
//                userResult.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[4].toString()));
//            }
            list.add(userStatisticsResult);
        }
        return list;
    }

    public List<UserStatisticsResult> findSatisticsResultByDate(Boolean isRegBySns) throws Exception
    {
        String listSql = "SELECT date(cdate) AS date,count(*) AS num , 30 - TIMESTAMPDIFF(DAY,date(cdate),date(now())) as deltaDays FROM allets_common.USER WHERE date_sub(date(now()),interval 30 day)<=date(cdate) GROUP BY date order by date DESC ;";
        if(isRegBySns) {
            listSql = "SELECT date(u.cdate) AS date,count(*) AS num ,30 - TIMESTAMPDIFF(DAY,date(u.cdate),date(now())) as deltaDays FROM USER_SNS us  left join  USER  u  on u.uid = us.uid  WHERE us.sns_type is not null and date_sub(date(now()),interval 30 day)<=date(u.cdate) GROUP BY date order by date DESC";
        }
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();

        List<UserStatisticsResult> list = new ArrayList<>();

        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserStatisticsResult userStatisticsResult = new UserStatisticsResult();
            if (obj[0] != null) {
                userStatisticsResult.setCdate(new SimpleDateFormat("yyyy-MM-dd").parse(obj[0].toString()));
            }
            if (obj[1] != null) {
                userStatisticsResult.setResult(Integer.valueOf(obj[1].toString()));
            }
            if (obj[2] != null) {
                userStatisticsResult.setType(obj[2].toString());
            }

            list.add(userStatisticsResult);
        }
        return list;
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
                " (SELECT count(follow_uid) FROM FOLLOW WHERE follow_uid=ur.uid) subscriberCount," +
                " intro_message," +
                " (select count(*) from MG_CONTENTS where uid=ur.uid) contentsCount," +
                " (select count(*) from MG_COMMENT where contents_id in (select contents_id from MG_CONTENTS where uid=ur.uid)) commentsCount, " +
                " (select count(*) from MG_BOOKMARK where contents_id in (select contents_id from MG_CONTENTS where uid=ur.uid)) bookmarkCount, " +
                " (select count(*) from MG_LIKE where contents_id in (select contents_id from MG_CONTENTS where uid=ur.uid)) likesCount " +
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
                userResult.setSubscriberCount(Integer.valueOf(obj[8].toString()));
            }
            if (obj[9] != null) {
                userResult.setIntroMessage(obj[9].toString());
            }
            if (obj[10] != null) {
                userResult.setContentsCount(Integer.valueOf(obj[10].toString()));
            }
            if (obj[11] != null) {
                userResult.setCommentCount(Integer.valueOf(obj[11].toString()));
            }
            if (obj[12] != null) {
                userResult.setLikeCount(Integer.valueOf(obj[12].toString()));
            }
            if (obj[13] != null) {
                userResult.setBookMarkCount(Integer.valueOf(obj[13].toString()));
            }

            userResultList.add(userResult);
        }

        Pageable pageable = new PageRequest(offset / limit, limit);
        PageImpl<UserResult> page = new PageImpl<UserResult>(userResultList, pageable, userResultList.size());
        return page;
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

}
