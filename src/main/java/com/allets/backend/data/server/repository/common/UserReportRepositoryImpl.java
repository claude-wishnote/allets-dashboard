package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserReportRepositoryImpl implements UserReportRepositoryCustom {

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;


    @Override
    public Page<UserResult> findAllReportedUser(Integer offset, Integer limit, String searchBy, String keyword, String sort) throws Exception {
        //1. using native Query, as is the query is too complicated if using queryDSL...

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ");
        //return fields
        StringBuilder sbReturnFields = new StringBuilder();
        sbReturnFields.append(" u.uid,");
        sbReturnFields.append(" u.name,");
        sbReturnFields.append(" ur.reportedCount,");
        sbReturnFields.append(" (SELECT cdate from USER_REPORT WHERE reported_uid=u.uid ORDER BY cdate DESC limit 1) as latestReportTime,");
        sbReturnFields.append(" (SELECT count(mc.comment_id) from MG_COMMENT mc where mc.uid=u.uid and mc.`status`='DEL') as deletedComment,");
        sbReturnFields.append(" (SELECT count(follow_uid) from FOLLOW where follow_uid= u.uid) as followCount,");
        sbReturnFields.append(" (select group_concat(DISTINCT(report_type)) from USER_REPORT where reported_uid=u.uid and report_type IS NOT NULL) report_type ");

        sb.append(sbReturnFields);

        // from
        sb.append(" FROM");

        sb.append(" USER u");
        sb.append(" INNER JOIN ");
        sb.append(" (SELECT MAX(urstat.id) lastId,urstat.reported_uid, count(urstat.reported_uid) reportedCount, urstat.cdate FROM USER_REPORT urstat WHERE urstat.handle_result is null GROUP BY urstat.reported_uid ) ur");
        sb.append(" ON u.uid = ur.reported_uid");

        //where
        sb.append(" WHERE");
        sb.append(" u.status !='DEL'");
        if (StringUtils.isNotEmpty(keyword) && StringUtils.isNotEmpty(searchBy)) {
            //TODO PUT keyword|email|nickName AS CONST
            if (searchBy.equals("keyword")) {
                sb.append(" AND ( u.intro_message LIKE '%" + keyword + "%' OR  u.name LIKE '%" + keyword + "%' )");
            } else if (searchBy.equals("nickName")) {
                sb.append(" AND u.name LIKE '%" + keyword + "%'");
            } else if (searchBy.equals("email")) {
                sb.append(" AND u.email LIKE '%" + keyword + "%'");
            } else if (searchBy.equals("uid")) {
                sb.append(" AND u.uid = '" + keyword + "'");
            }
        }

        // order, offset, limit
        StringBuilder sbOrderPage = new StringBuilder();
        sbOrderPage.append(" ORDER BY");
        if (StringUtils.isNotEmpty(sort)) {
            if (sort.equals("+regTime")) {
                sbOrderPage.append(" ur.lastId ASC");
            } else if (sort.equals("+reportCount")) {
                sbOrderPage.append(" ur.reportedCount ASC");
            } else if (sort.equals("-regTime")) {
                sbOrderPage.append(" ur.lastId DESC");
            } else {
                sbOrderPage.append(" ur.reportedCount DESC");
            }
        } else {
            sbOrderPage.append(" ur.reportedCount DESC");
        }
        sbOrderPage.append(" LIMIT ").append(limit);
        sbOrderPage.append(" OFFSET ").append(offset);

        sb.append(sbOrderPage);

        String listSql = sb.toString();

        //2. query
        Query listQuery = entityManager.createNativeQuery(listSql);
        List objecArraytList = listQuery.getResultList();

        //3. return result
        List<UserResult> resultList = new ArrayList<UserResult>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            UserResult result = new UserResult();
            result.setUid(Long.valueOf(obj[0].toString()));
            result.setName(String.valueOf(obj[1]));
            result.setReportedCount(Integer.valueOf(obj[2].toString()));
            result.setLatestReportTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[3].toString()));
            result.setDeleteCommentsCount(Integer.valueOf(obj[4].toString()));
            result.setSubscriberCount(Integer.valueOf(obj[5].toString()));
            if (obj[6] != null) {
                result.setReportType(obj[6].toString());
            }
            resultList.add(result);
        }

        //4. get total count
        String countSql = listSql.replace(sbReturnFields.toString(), " count(*) ").replace(sbOrderPage.toString(), "");
        Query countQuery = entityManager.createNativeQuery(countSql);
        List countArraytList = countQuery.getResultList();
        Long count = Long.valueOf(countArraytList.get(0).toString());


        //5. return
        Pageable pageable = new PageRequest(offset / limit, limit);
        return new PageImpl<UserResult>(resultList, pageable, count);
    }

    @Override
    public ReportTypeCountResult findReportTypeCountResult(Long uid) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT ur.report_type,count(*) FROM USER_REPORT ur where ur.reported_uid = ");
        listSb.append(uid);
        listSb.append(" GROUP BY ur.report_type");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
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
}
