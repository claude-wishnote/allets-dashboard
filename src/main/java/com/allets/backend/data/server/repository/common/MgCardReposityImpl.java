package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.MgCardResult;
import com.allets.backend.data.server.exception.BadRequestException;
import com.allets.backend.data.server.exception.ParameterTypeErrorException;
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
public class MgCardReposityImpl implements MgCardReposityCustom {

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;
    @Override
    public List<MgCardResult> findMgCards(String q) throws Exception {
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

        if (parametersMap.containsKey("contentsId")) {
            listSql = "SELECT card_id,url,card_type,ordering,title,description,contents_id,video_thumbnail_url FROM MG_CARD WHERE contents_id=";
            String contentsIdString = (String) parametersMap.get("contentsId");
            try{
            Long contentsId = Long.valueOf(contentsIdString);}
            catch (Exception e){
                throw new ParameterTypeErrorException();
            }
            listSql = listSql +(String) parametersMap.get("contentsId") + " ORDER BY ordering ASC";
            listQuery = entityManager.createNativeQuery(listSql);
            objecArraytList = listQuery.getResultList();
        }else
        {
            objecArraytList = new ArrayList<>();
        }
        if (objecArraytList == null) {
            throw new BadRequestException();
        }
        List<MgCardResult> mgCardRList = new ArrayList<MgCardResult>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            MgCardResult mgCardResult = new MgCardResult();
            mgCardResult.setCardId(Long.valueOf(obj[0].toString()));
            if (obj[1] != null) {
                mgCardResult.setUrl(obj[1].toString());
            }
            if (obj[2] != null) {
                mgCardResult.setCardType(obj[2].toString());
            }
            if (obj[3] != null) {
                mgCardResult.setOrdering(Integer.valueOf(obj[3].toString()));
            }
            if (obj[4] != null) {
                mgCardResult.setTitle(obj[4].toString());
            }
            if (obj[5] != null) {
                mgCardResult.setDescription(obj[5].toString());
            }
            if (obj[6] != null) {
                mgCardResult.setContentsId(Long.valueOf(obj[6].toString()));
            }
            if (obj[7] != null) {
                mgCardResult.setVideoThumbnailUrl(obj[7].toString());
            }
            mgCardRList.add(mgCardResult);
        }
        return  mgCardRList;
    }
}
