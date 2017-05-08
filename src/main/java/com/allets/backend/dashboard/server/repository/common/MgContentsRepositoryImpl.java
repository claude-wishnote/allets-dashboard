package com.allets.backend.dashboard.server.repository.common;

import com.allets.backend.dashboard.server.consts.Const;
import com.allets.backend.dashboard.server.data.result.MgContentsResult;
import com.allets.backend.dashboard.server.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
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

/**
 * Created by claude on 2016/1/24.
 */
public class MgContentsRepositoryImpl implements MgContentsRepositoryCustom {

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;
    @Resource(name = "redisTemplate")
    private HashOperations hashOperations;

    @Override
    public PageImpl<MgContentsResult> findMgContents(String q, Integer offset, Integer limit) throws Exception {

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

        String countSql = "SELECT " +
                " COUNT(*)" +
                " FROM MG_CONTENTS mg WHERE mg.contents_type != 'PAST'  ";

        if (parametersMap != null) {
            if (parametersMap.containsKey("title")) {
                countSql = countSql + " AND mg.title LIKE  '%" + (String) parametersMap.get("title")+"%'";
            }
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    countSql = countSql + " AND mg.udate >= '" + startDate + "' AND mg.udate < '" + endDate + "' ";
//                    countSql = countSql +
//                            " AND (mg.udate BETWEEN '" + startDate + "'" +
//                            " AND  '" + endDate + "')";
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    countSql = countSql + " AND mg.udate >= '" + startDate + "' ";
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    countSql = countSql + " AND mg.udate < '" + endDate + "' ";
                }
            }
        }

        Query countQuery = entityManager.createNativeQuery(countSql);
        Object o = countQuery.getSingleResult();
        Long count = Long.valueOf(o.toString());

        String listSql = "";
        Query listQuery = null;
        List objecArraytList = null;
        //content should be normal content but not serie(PAST)
        listSql = "SELECT mg.contents_id,mg.title,mg.contents_type,mg.uid,mg.udate, " +
                "(select name from USER u where u.uid = mg.uid) name, " +
                "(select count(*) from MG_COMMENT mgc where mgc.contents_id = mg.contents_id and mgc.status = 'ACTV') commentCount, " +
                "(select count(*) from MG_BOOKMARK mgb where mgb.contents_id = mg.contents_id) bookmarkCount, " +
                "(select count(*) from MG_LIKE mgl where mgl.contents_id = mg.contents_id) likeCount, " +
                " mg.cdate " +
                " FROM MG_CONTENTS mg WHERE mg.contents_type != 'PAST' ";

        if (parametersMap != null) {
            if (parametersMap.containsKey("title")) {
                listSql = listSql + " AND mg.title LIKE  '%" + (String) parametersMap.get("title")+"%'";
            }
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSql = listSql + " AND mg.udate >= '" + startDate + "' AND mg.udate < '" + endDate + "' ";
//                    listSql = listSql +
//                            " AND (mg.udate BETWEEN '" + startDate + "'" +
//                            " AND  '" + endDate + "')";
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSql = listSql + " AND mg.udate >= '" + startDate + "' ";
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSql = listSql + " AND mg.udate < '" + endDate + "' ";
                }
            }
        }


        listSql = listSql + "order by mg.contents_id";
        listSql = listSql + " LIMIT " + limit + " OFFSET " + offset;
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
            if (obj[0] != null) {
                mgContentsResult.setContentsId(Long.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                mgContentsResult.setTitle(obj[1].toString());
            }
            if (obj[2] != null) {
                mgContentsResult.setContentsType(obj[2].toString());
            }
            if (obj[3] != null) {
                mgContentsResult.setUid(Integer.valueOf(obj[3].toString()));
            }
            if (obj[4] != null) {
                mgContentsResult.setUdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[4].toString()));
            }
            if (obj[5] != null) {
                mgContentsResult.setEditorName(obj[5].toString());
            }
            if (obj[6] != null) {
                mgContentsResult.setCommentCount(Integer.valueOf(obj[6].toString()));
            }
            if (obj[7] != null) {
                mgContentsResult.setBookmarkCount(Integer.valueOf(obj[7].toString()));
            }
            if (obj[8] != null) {
                mgContentsResult.setLikeCount(Integer.valueOf(obj[8].toString()));
            }
            if (obj[9] != null) {
                mgContentsResult.setCdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[9].toString()));
            }
            mgContentsResult.setAppViewCount(findViewCount(mgContentsResult.getContentsId()));
            mgContentsResult.setWebViewCount(findWebViewCount(mgContentsResult.getContentsId()));
            mgContentsResult.setShareCount(findShareCount(mgContentsResult.getContentsId()));
//            mgContentsResult.setAppViewCount(999);
//            mgContentsResult.setWebViewCount(998);
//            mgContentsResult.setShareCount("");

            mgContentsList.add(mgContentsResult);
        }

        Pageable pageable = new PageRequest(offset / limit, limit);
        return new PageImpl<MgContentsResult>(mgContentsList, pageable, count);
    }

     public String findShareCount(Long uid) throws Exception {
        if (uid == null) {
             return null;
        }

        String hashKeyRightSide = ":" + (uid / 1000);
        String shareCount = "";

         String restKeyEmail = "CONTENTS_SHARE_COUNT_" + Const.UserSnsType.EMAIL + hashKeyRightSide;
         String restKeyWebEmail = "CONTENTS_SHARE_COUNT_WEB_" + Const.UserSnsType.EMAIL + hashKeyRightSide;
         String restKeyFacebook = "CONTENTS_SHARE_COUNT_" + Const.UserSnsType.FACEBOOK + hashKeyRightSide;
         String restKeyWebFacebook = "CONTENTS_SHARE_COUNT_WEB_" + Const.UserSnsType.FACEBOOK + hashKeyRightSide;
         String restKeyKakao = "CONTENTS_SHARE_COUNT_" + Const.UserSnsType.KAKAO + hashKeyRightSide;
         String restKeyWebKakao = "CONTENTS_SHARE_COUNT_WEB_" + Const.UserSnsType.KAKAO + hashKeyRightSide;
         String restKeyLine = "CONTENTS_SHARE_COUNT_" + Const.UserSnsType.LINE + hashKeyRightSide;
         String restKeyWebLine = "CONTENTS_SHARE_COUNT_WEB_" + Const.UserSnsType.LINE + hashKeyRightSide;
         String restKeyTwitter = "CONTENTS_SHARE_COUNT_" + Const.UserSnsType.TWITTER + hashKeyRightSide;
         String restKeyWebTwitter = "CONTENTS_SHARE_COUNT_WEB_" + Const.UserSnsType.TWITTER + hashKeyRightSide;

         Integer emailShareCount = 0;
         Integer webEmailShareCount = 0;
         Integer facebookShareCount = 0;
         Integer webFacebookShareCount = 0;
         Integer kakaoShareCount = 0;
         Integer webKakaoShareCount = 0;
         Integer lineShareCount = 0;
         Integer webLineShareCount = 0;
         Integer twitterShareCount = 0;
         Integer webTwitterShareCount = 0;

         String[] resKeys = {restKeyEmail,restKeyWebEmail,restKeyFacebook,restKeyWebFacebook,restKeyKakao,restKeyWebKakao,restKeyLine,restKeyWebLine,restKeyTwitter,restKeyWebTwitter};
         Integer[] valuse = {emailShareCount,webEmailShareCount,facebookShareCount,webFacebookShareCount,kakaoShareCount,webKakaoShareCount,lineShareCount,webLineShareCount,twitterShareCount,webTwitterShareCount};
         String hashKey = uid.toString();

         for(int i=0;i<10;i++)
         {
             String cuntString = (String) hashOperations.get(resKeys[i], hashKey);
             if(cuntString == null)
             {
                 valuse[i] = 0;
             }else
             {
                 valuse[i] = Integer.valueOf(cuntString);
             }
         }

         //        else {
         //            hashOperations.put(restKey, hashKey, String.valueOf(0));
         //        }

         shareCount = "Email:"+(valuse[0]+valuse[1]) +"<br>"+
         "FB:"+(valuse[2]+valuse[3]) +"<br>"+
         "KT:"+(valuse[4]+valuse[5]) +"<br>"+
         "Line:"+(valuse[6]+valuse[7]) +"<br>"+
         "Twitter:"+(valuse[8]+valuse[9]) +"<br>";

         return shareCount;
    }

    public Integer findWebShareCount(Long uid) throws Exception {
        if (uid == null) {
            return null;
        }

        String hashKeyRightSide = ":" + (uid / 1000);
        String shareCount = null;

        String restKey = "CONTENTS_SHARE_COUNT_WEB" + hashKeyRightSide;
        String hashKey = uid.toString();

        if (hashOperations.hasKey(restKey, hashKey)) {
            shareCount = (String) hashOperations.get(restKey, hashKey);
        }
//        else {
//            hashOperations.put(restKey, hashKey, String.valueOf(0));
//        }

        if (shareCount == null)
            shareCount = "0";

        return Integer.valueOf(shareCount);
    }

    public Integer findViewCount(Long uid) throws Exception {
        if (uid == null) {
            return null;
        }

        String hashKeyRightSide = ":" + (uid / 1000);
        String shareCount = null;

        String restKey = "CONTENTS_VIEW_COUNT" + hashKeyRightSide;
        String hashKey = uid.toString();

        if (hashOperations.hasKey(restKey, hashKey)) {
            shareCount = (String) hashOperations.get(restKey, hashKey);
        }
//        else {
//            hashOperations.put(restKey, hashKey, String.valueOf(0));
//        }

        if (shareCount == null)
            shareCount = "0";

        return Integer.valueOf(shareCount);
    }

    public Integer findWebViewCount(Long uid) throws Exception {
        if (uid == null) {
            return null;
        }

        String hashKeyRightSide = ":" + (uid / 1000);
        String shareCount = null;

        String restKey = "CONTENTS_VIEW_COUNT_WEB" + hashKeyRightSide;
        String hashKey = uid.toString();

        if (hashOperations.hasKey(restKey, hashKey)) {
            shareCount = (String) hashOperations.get(restKey, hashKey);
        }
//        else {
//            hashOperations.put(restKey, hashKey, String.valueOf(0));
//        }

        if (shareCount == null)
            shareCount = "0";

        return Integer.valueOf(shareCount);
    }

    public Boolean checkDate(String date) {
        return date != null && !date.isEmpty() & !date.equals("Invalid Date");
    }

}
