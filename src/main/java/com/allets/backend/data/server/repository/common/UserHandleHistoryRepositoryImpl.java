package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.UserHandleHistoryResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2015/9/6.
 */
public class UserHandleHistoryRepositoryImpl implements UserHandleHistoryRepositoryCustom {
    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    @Override
    public List<UserHandleHistoryResult> findUserHandleHistoryByUid(Long uid) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT id,handle_result,max(cdate) FROM USER_HANDLE_HISTORY WHERE (handle_result='ALT1' OR handle_result='ALT2')");
        listSb.append(" AND uid=");
        listSb.append(uid);
        listSb.append(" group by handle_result");

//      listSb.append(" ORDER BY id DESC LIMIT 1 OFFSET 0 ");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        List<UserHandleHistoryResult> resultList = new ArrayList<UserHandleHistoryResult>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserHandleHistoryResult result = new UserHandleHistoryResult();
            if (obj[0] != null) {
                result.setId(Long.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                result.setHandleResult(obj[1].toString());
            }
            if (obj[2] != null) {
                result.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[2].toString()));
            }
            resultList.add(result);
        }
        return resultList;
    }
}
