package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.utils.JsonUtil;
import com.allets.backend.data.server.entity.common.UserBlackList;
import com.allets.backend.data.server.data.result.UserBlackResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jack on 2015/9/10.
 */

public class BlackListRepositoryImpl implements BlackListRepositoryCustom {

    final Logger log = LoggerFactory.getLogger(BlackListRepositoryImpl.class);

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    @Override
    public PageImpl<UserBlackResult> findAllBlackList(String parameters, Integer offset, Integer limit) throws Exception {

        HashMap<String, Object> parametersMap = new HashMap<>();
        if (StringUtils.isNotBlank(parameters)) {
            parameters = URLDecoder.decode(parameters, "utf-8");
            String[] conditions = parameters.split(",");
            if (conditions != null & conditions.length > 0) {
                for (String parameter : conditions) {
                    String[] values = parameter.split("=");
                    parametersMap.put(values[0], values[1]);
                }
            }
        }
        StringBuffer sqlBuffer = new StringBuffer();
        //get base data
        sqlBuffer.append("SELECT \n" +
                "    (ub.id) id,\n" +
                "    (u.name) nickName,\n" +
                "    (u.cdate) cdate,\n" +
                "    (SELECT \n" +
                "            COUNT(*)\n" +
                "        FROM\n" +
                "            USER_REPORT\n" +
                "        WHERE\n" +
                "            reported_uid = ub.uid) reportedCount,\n" +
                "    (SELECT \n" +
                "            COUNT(*)\n" +
                "        FROM\n" +
                "            MG_COMMENT\n" +
                "        WHERE\n" +
                "            uid = ub.uid AND status = 'DEL') commentDeleteCount,\n" +
                "    (SELECT \n" +
                "            COUNT(*)\n" +
                "        FROM\n" +
                "            FOLLOW\n" +
                "        WHERE\n" +
                "            follow_uid = ub.uid) followerCount,\n" +
                "    (u.email) email,\n" +
                "    (ub.uid) uid,\n" +
                "    (SELECT \n" +
                "            name\n" +
                "        FROM\n" +
                "            MONITOR\n" +
                "        WHERE\n" +
                "            monitor_id = ub.monitor_id) monitorName\n" +
                "FROM\n" +
                "    USER_BLACK_LIST ub\n" +
                "        INNER JOIN\n" +
                "    USER u ON (ub.uid = u.uid)\n" +
                "WHERE\n" +
                "    1 = 1\n");

        // add parameters
        if (parametersMap != null) {
            Iterator iter = parametersMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                if (key.equals("email")) {
                    sqlBuffer.append(" AND u.email LIKE '%" + val + "%' ");
                }
                if (key.equals("nickName")) {
                    sqlBuffer.append(" AND u.name LIKE '%" + val + "%' ");
                }
                if (key.equals("keyword")) {
                    sqlBuffer.append(" AND CONCAT(u.email, u.name) LIKE '%" + val + "%'");
                }
            }
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                sqlBuffer.append(" AND ub.cdate BETWEEN '" + parametersMap.get("startDate") + "' AND '" + parametersMap.get("endDate") + "' ");
            } else if (parametersMap.containsKey("startDate")) {
                sqlBuffer.append(" AND ub.cdate >= '" + parametersMap.get("startDate") + "' ");
            } else if (parametersMap.containsKey("endDate")) {
                sqlBuffer.append(" AND ub.cdate <= '" + parametersMap.get("endDate") + "' ");
            }
        }

        //get data count
        Integer count = entityManager.createNativeQuery(sqlBuffer.toString()).getResultList().size();

        //add offset and limit
        sqlBuffer.append("LIMIT " + (limit == null ? 15 : limit) + " OFFSET " + (offset == null ? 0 : offset) + "");

        List<Object[]> listResults = entityManager.createNativeQuery(sqlBuffer.toString()).getResultList();
        List<UserBlackResult> blackResults = new ArrayList<UserBlackResult>();
        for (Object[] record : listResults) {
            UserBlackResult blackResult = new UserBlackResult();
            blackResult.setId(Integer.valueOf(record[0].toString()));
            blackResult.setNickName(String.valueOf(record[1]));
            blackResult.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(record[2].toString()));
            blackResult.setReportedCount(Integer.valueOf(record[3].toString()));
            blackResult.setCommentDeleteCount(Integer.valueOf(record[4].toString()));
            blackResult.setFollowerCount(Integer.valueOf(record[5].toString()));
            blackResult.setEmail(String.valueOf(record[6] == null ? "" : record[6]));
            blackResult.setUid(Integer.valueOf(record[7].toString()));
            blackResult.setMonitorName(String.valueOf(record[8]));
            blackResults.add(blackResult);

        }
        log.info(JsonUtil.marshallingJson(count));
        Pageable pageable = new PageRequest(offset / limit, limit);
        return new PageImpl<UserBlackResult>(blackResults, pageable, count);
    }

    @Override
    public UserBlackList createBlackList(UserBlackList userBlackList) throws Exception {
//        QUserBlackList qUserBlackList = QUserBlackList.userBlackList;
//        String listSql = "INSERT INTO "
//        Query listQuery = entityManager.createNativeQuery(listSql);

        return null;
    }
}
