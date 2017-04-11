package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.MgContentsResult;
import com.allets.backend.data.server.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
public class MgContentsRepositoryImpl implements MgContentsRepositoryCustom {

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    @Override
    public List<MgContentsResult> findMgContents(String q, Integer offset, Integer limit) throws Exception {

        HashMap<String, Object> parametersMap = new HashMap<>();
        if (StringUtils.isNotBlank(q)) {
            q = URLDecoder.decode(q, "utf-8");
            String[] conditions = q.split(",");
            if (conditions != null & conditions.length > 0) {
                for (String parameter : conditions) {
                    String[] values = parameter.split("=");
                    if(values.length>1) {
                        parametersMap.put(values[0], values[1]);
                    }
                }
            }
        }

        String listSql = "";
        Query listQuery = null;
        List objecArraytList = null;
        //content should be normal content but not serie(PAST)
        listSql = "SELECT contents_id,title,contents_type FROM MG_CONTENTS WHERE contents_type != 'PAST'";

        if (parametersMap != null) {
            if (parametersMap.containsKey("title")) {
                listSql = listSql + " AND title LIKE  '%" + (String) parametersMap.get("title")+"%'";
            }
        }
        listSql = listSql + " LIMIT 20 OFFSET 0 ";
        listQuery = entityManager.createNativeQuery(listSql);
        objecArraytList = listQuery.getResultList();
        if (objecArraytList == null) {
            throw new BadRequestException();
        }

        List<MgContentsResult> mgContentsList = new ArrayList<MgContentsResult>();

        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            MgContentsResult mgContentsResult = new MgContentsResult();
            mgContentsResult.setContentsId(Long.valueOf(obj[0].toString()));
            if (obj[1] != null) {
                mgContentsResult.setTitle(obj[1].toString());
            }
            if (obj[2] != null) {
                mgContentsResult.setContentsType(obj[2].toString());
            }
            mgContentsList.add(mgContentsResult);
        }
        return mgContentsList;
    }
}
