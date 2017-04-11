package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.TimeLineStatistics;
import com.allets.backend.data.server.data.result.MonitorStatistics;
import com.allets.backend.data.server.data.result.StatisticsListsHolder;
import com.allets.backend.data.server.entity.common.Monitor;
import com.allets.backend.data.server.entity.common.QMonitor;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.data.result.ContentsStatistics;
import com.allets.backend.data.server.utils.AwsUpload;
import com.allets.backend.data.server.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by claude on 2016/1/31.
 */
public class MonitorRepositoryImpl implements MonitorRepositoryCustum {
    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;
    @Autowired
    AwsUpload awsUpload;

    @Override
    public List<MonitorStatistics> findUserHandleHistory(String monitorIds, String q) throws Exception {
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
        String[] idConditions = null;
        if (StringUtils.isNotBlank(monitorIds)) {
            idConditions = monitorIds.split(",");
        }
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT ");
        listSb.append("monitor_id monitorId,");
        listSb.append("name,");
        listSb.append("last_login_time lastAccessDate,");
        if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
            if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                String startDate = (String) parametersMap.get("startDate");
                String endDate = (String) parametersMap.get("endDate");
                listSb.append("(select count(*) from COMMENT_HANDLE_HISTORY where monitor_id = m.monitor_id ").append(" AND (cdate BETWEEN '").append(startDate).append("' AND  '").append(endDate).append("')").append(")  deleteCommentsCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where (handle_result='ALT1' or handle_result='ALT2') and monitor_id = m.monitor_id ").append(" AND (cdate BETWEEN '").append(startDate).append("' AND  '").append(endDate).append("')").append(") sendAlertCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='BLOK'and monitor_id = m.monitor_id ").append(" AND (cdate BETWEEN '").append(startDate).append("' AND  '").append(endDate).append("')").append(") accountHoldCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='DEL'and monitor_id = m.monitor_id ").append(" AND (cdate BETWEEN '").append(startDate).append("' AND  '").append(endDate).append("')").append(") accountDeleteCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='EDIT'and monitor_id = m.monitor_id ").append(" AND (cdate BETWEEN '").append(startDate).append("' AND  '").append(endDate).append("')").append(") accountResetCount ");

            }
        } else if (parametersMap.containsKey("startDate")) {
            if (checkDate((String) parametersMap.get("startDate"))) {
                String startDate = (String) parametersMap.get("startDate");
                listSb.append("(select count(*) from COMMENT_HANDLE_HISTORY where monitor_id = m.monitor_id ").append(" AND cdate >= '").append(startDate).append("' ").append(")  deleteCommentsCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where (handle_result='ALT1' or handle_result='ALT2') and monitor_id = m.monitor_id ").append(" AND cdate >= '").append(startDate).append("' ").append(") sendAlertCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='BLOK'and monitor_id = m.monitor_id ").append(" AND cdate >= '").append(startDate).append("' ").append(") accountHoldCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='DEL'and monitor_id = m.monitor_id ").append(" AND cdate >= '").append(startDate).append("' ").append(") accountDeleteCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='EDIT'and monitor_id = m.monitor_id ").append(" AND cdate >= '").append(startDate).append("' ").append(") accountResetCount ");
            }
        } else if (parametersMap.containsKey("endDate")) {
            if (checkDate((String) parametersMap.get("endDate"))) {
                String endDate = (String) parametersMap.get("endDate");
                listSb.append("(select count(*) from COMMENT_HANDLE_HISTORY where monitor_id = m.monitor_id ").append(" AND cdate <= '").append(endDate).append("' ").append(")  deleteCommentsCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where (handle_result='ALT1' or handle_result='ALT2') and monitor_id = m.monitor_id ").append(" AND cdate <= '").append(endDate).append("' ").append(") sendAlertCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='BLOK'and monitor_id = m.monitor_id ").append(" AND cdate <= '").append(endDate).append("' ").append(") accountHoldCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='DEL'and monitor_id = m.monitor_id ").append(" AND cdate <= '").append(endDate).append("' ").append(") accountDeleteCount,");
                listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='EDIT'and monitor_id = m.monitor_id ").append(" AND cdate <= '").append(endDate).append("' ").append(") accountResetCount ");
            }
        } else {
            listSb.append("(select count(*) from COMMENT_HANDLE_HISTORY where monitor_id = m.monitor_id)  deleteCommentsCount,");
            listSb.append("(select count(*) from USER_HANDLE_HISTORY where (handle_result='ALT1' or handle_result='ALT2') and monitor_id = m.monitor_id) sendAlertCount,");
            listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='BLOK'and monitor_id = m.monitor_id) accountHoldCount,");
            listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='DEL'and monitor_id = m.monitor_id) accountDeleteCount,");
            listSb.append("(select count(*) from USER_HANDLE_HISTORY where handle_result='EDIT'and monitor_id = m.monitor_id) accountResetCount ");
        }
        listSb.append("FROM MONITOR m WHERE 1=1 ");
        if (idConditions != null) {
            for (String monitorId : idConditions) {
                if (monitorId == idConditions[0]) {
                    listSb.append(" AND (");
                }
                listSb.append(" monitor_id=");
                listSb.append(monitorId);
                if (monitorId != idConditions[idConditions.length - 1]) {
                    listSb.append(" OR ");
                } else {
                    listSb.append(" )");
                }
            }
        }

        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        List<MonitorStatistics> resultList = new ArrayList<MonitorStatistics>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            MonitorStatistics result = new MonitorStatistics();
            if (obj[0] != null) {
                result.setMonitorId(Long.valueOf(obj[0].toString()));
            }
            if (obj[1] != null) {
                result.setName(obj[1].toString());
            }
            if (obj[2] != null) {
                result.setLastAccessDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[2].toString()));
            }
            if (obj[3] != null) {
                result.setDeleteCommentsCount(Integer.valueOf(obj[3].toString()));
            }
            if (obj[4] != null) {
                result.setSendAlertCount(Integer.valueOf(obj[4].toString()));
            }
            if (obj[5] != null) {
                result.setAccountHoldCount(Integer.valueOf(obj[5].toString()));
            }
            if (obj[6] != null) {
                result.setAccountDeleteCount(Integer.valueOf(obj[6].toString()));
            }
            if (obj[7] != null) {
                result.setAccountResetCount(Integer.valueOf(obj[7].toString()));
            }
            resultList.add(result);
        }
        return resultList;
    }

    public Boolean checkDate(String date) {
        return date != null && !date.isEmpty() & !date.equals("Invalid Date");
    }

    @Override
    public void updateMonitorLastLoginTime(Long monitorId) throws Exception {
        QMonitor qMonitor = QMonitor.monitor;
        JPAUpdateClause qMonitorUpdate = new JPAUpdateClause(entityManager, qMonitor);
        qMonitorUpdate.where(qMonitor.monitorId.eq(monitorId))
                .set(qMonitor.lastLoginTime, new Date());
        qMonitorUpdate.execute();
    }

    StatisticsListsHolder findContentsStatisticsList(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT date(chh.cdate),mgc.title,mc.contents_id,mgc.udate,(select GROUP_CONCAT(report_type)  from COMMENT_REPORT where comment_id = chh.comment_id) report_types,");
        listSb.append("(SELECT GROUP_CONCAT(u.email) FROM COMMENT_REPORT x LEFT JOIN USER u ON x.uid=u.uid WHERE x.comment_id=mc.comment_id");
        for (int i = 0; i < Const.InHouseUsers.size(); i++) {
            String email = Const.InHouseUsers.get(i);
            if (i == 0) {
                listSb.append(" AND (u.email='" + email + "' ");
            } else if (i == Const.InHouseUsers.size() - 1) {
                listSb.append(" OR u.email='" + email + "') ");
            } else {
                listSb.append(" OR u.email='" + email + "'");
            }
        }
        listSb.append(") inhouseAccounts, ");
        listSb.append(" (SELECT GROUP_CONCAT(gc.cate_name) FROM CATEGORY gc WHERE gc.cate_id in (SELECT mcc.cate_id FROM MG_CATEGORY_CONTENTS mcc WHERE mcc.contents_id = mgc.contents_id)) Category");
        listSb.append(" FROM COMMENT_HANDLE_HISTORY chh LEFT JOIN MG_COMMENT mc ON chh.comment_id = mc.comment_id LEFT JOIN MG_CONTENTS mgc ON mc.contents_id = mgc.contents_id");
        listSb.append(" WHERE chh.handle_result IN ('HIDD' , 'DEL')");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (chh.cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND chh.cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND chh.cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append(" GROUP BY mgc.contents_id,mc.comment_id,DATE(chh.cdate) ORDER BY chh.cdate,mgc.contents_id;");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        List<ContentsStatistics> resultList = new ArrayList<ContentsStatistics>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            ContentsStatistics result = new ContentsStatistics();
            if (obj[0] != null) {
                result.setHandleDateString(obj[0].toString());
            }
            if (obj[1] != null) {
                result.setContentName(obj[1].toString());
            }
            if (obj[2] != null) {
                result.setContentsId(Long.valueOf(obj[2].toString()));
            }
            if (obj[3] != null) {
                result.setPublishDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(obj[3].toString()));
            }
            if (obj[4] != null) {
                result.setReportTypes(obj[4].toString());
            }
            if (obj[5] != null) {
                result.setInHouseAccounts(obj[5].toString());
            }
            if (obj[6] != null) {
                result.setCategory(obj[6].toString());
            }

            resultList.add(result);
        }
        StringBuilder titleSb = new StringBuilder();
        titleSb.append("statistics_beginning_to_end_");
        titleSb.append(System.currentTimeMillis());
        String title = titleSb.toString();
        if (resultList.size() == 0) {
            ContentsStatistics nullContentsStatistics = new ContentsStatistics();
            nullContentsStatistics.setHandleDateString("");
            resultList.add(nullContentsStatistics);
        }
        if (StringUtils.isEmpty((String) parametersMap.get("startDate"))) {
            if (!StringUtils.isEmpty(resultList.get(0).getHandleDateString())) {
                title = title.replace("beginning", resultList.get(0).getHandleDateString());
            }
        } else {
            title = title.replace("beginning", (String) parametersMap.get("startDate"));
        }
        if (StringUtils.isEmpty((String) parametersMap.get("endDate"))) {
            title = title.replace("end", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } else {
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) parametersMap.get("endDate"));
            Calendar date = Calendar.getInstance();
            date.setTime(endDate);
            String endDateString = new SimpleDateFormat("yyyy-MM-dd").format(date.getTimeInMillis() - 86400000);
            title = title.replace("end", endDateString);
        }

        statisticsListsHolder.setContentsStatisticsList(setReportTypeDelNumArrayForContentsStatisticsList(resultList));
        statisticsListsHolder.getSheetNameList().add("statistics");
        statisticsListsHolder.getTableTitlesList().add(title);
        return statisticsListsHolder;
    }

    List<ContentsStatistics> setReportTypeDelNumArrayForContentsStatisticsList(List<ContentsStatistics> resultList) {
        //set ReportTypeDelNumArray
        List<ContentsStatistics> newResultList = new ArrayList<ContentsStatistics>();
        ContentsStatistics tempContentsStatistics = null;
        String reportTypes = null;
        Pattern p = Pattern.compile("[\\uAC00-\\uD7A3]");
        for (int i = 0; i < resultList.size(); i++) {
            ContentsStatistics contentsStatistics = resultList.get(i);
            if (tempContentsStatistics == null) {
                tempContentsStatistics = contentsStatistics;
            }
            if (!contentsStatistics.getContentsId().equals(tempContentsStatistics.getContentsId())
                    || !contentsStatistics.getHandleDateString().equals(tempContentsStatistics.getHandleDateString())) {
                String category = tempContentsStatistics.getCategory();
                if (StringUtils.isBlank(category)) {
                } else if (p.matcher(category).find()) {
                    category = category.replace(",MAIN", "").replace("MAIN,", "").replace("MAIN", "");
                    category = category.replace(",ETC", "").replace("ETC,", "").replace("ETC", "");
                    category = category.replace(",HEAD", "").replace("HEAD,", "").replace("HEAD", "");
                    tempContentsStatistics.setCategory(category);
                } else if (category.contains("ETC")) {
                    tempContentsStatistics.setCategory("ETC");
                } else if (category.contains("MAIN")) {
                    tempContentsStatistics.setCategory("MAIN");
                } else if (category.contains("HEAD")) {
                    tempContentsStatistics.setCategory("HEAD");
                }
                tempContentsStatistics.setDeleteCommentNumber(tempContentsStatistics.getReportTypeDelNumArray()[0]
                                + tempContentsStatistics.getReportTypeDelNumArray()[1]
                                + tempContentsStatistics.getReportTypeDelNumArray()[2]
                                + tempContentsStatistics.getReportTypeDelNumArray()[3]
                                + tempContentsStatistics.getReportTypeDelNumArray()[4]
                                + tempContentsStatistics.getReportTypeDelNumArray()[5]
                                + tempContentsStatistics.getReportTypeDelNumArray()[6]
                );
                newResultList.add(tempContentsStatistics);
                tempContentsStatistics = contentsStatistics;
                reportTypes = contentsStatistics.getReportTypes();
            }

            if (reportTypes == null) {
                tempContentsStatistics.getReportTypeDelNumArray()[6] = tempContentsStatistics.getReportTypeDelNumArray()[6] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT100)) {
                tempContentsStatistics.getReportTypeDelNumArray()[0] = tempContentsStatistics.getReportTypeDelNumArray()[0] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT200)) {
                tempContentsStatistics.getReportTypeDelNumArray()[1] = tempContentsStatistics.getReportTypeDelNumArray()[1] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT300)) {
                tempContentsStatistics.getReportTypeDelNumArray()[2] = tempContentsStatistics.getReportTypeDelNumArray()[2] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT400)) {
                tempContentsStatistics.getReportTypeDelNumArray()[3] = tempContentsStatistics.getReportTypeDelNumArray()[3] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT500)) {
                tempContentsStatistics.getReportTypeDelNumArray()[4] = tempContentsStatistics.getReportTypeDelNumArray()[4] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT600)) {
                tempContentsStatistics.getReportTypeDelNumArray()[5] = tempContentsStatistics.getReportTypeDelNumArray()[5] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT999)) {
                tempContentsStatistics.getReportTypeDelNumArray()[6] = tempContentsStatistics.getReportTypeDelNumArray()[6] + 1;
            }
            if (i == resultList.size() - 1) {
                String category = tempContentsStatistics.getCategory();
                if (StringUtils.isBlank(category)) {
                } else if (p.matcher(category).find()) {
                    category = category.replace(",MAIN", "").replace("MAIN,", "").replace("MAIN", "");
                    category = category.replace(",ETC", "").replace("ETC,", "").replace("ETC", "");
                    category = category.replace(",HEAD", "").replace("HEAD,", "").replace("HEAD", "");
                    tempContentsStatistics.setCategory(category);
                } else if (category.contains("ETC")) {
                    tempContentsStatistics.setCategory("ETC");
                } else if (category.contains("MAIN")) {
                    tempContentsStatistics.setCategory("MAIN");
                } else if (category.contains("HEAD")) {
                    tempContentsStatistics.setCategory("HEAD");
                }
                tempContentsStatistics.setDeleteCommentNumber(tempContentsStatistics.getReportTypeDelNumArray()[0]
                                + tempContentsStatistics.getReportTypeDelNumArray()[1]
                                + tempContentsStatistics.getReportTypeDelNumArray()[2]
                                + tempContentsStatistics.getReportTypeDelNumArray()[3]
                                + tempContentsStatistics.getReportTypeDelNumArray()[4]
                                + tempContentsStatistics.getReportTypeDelNumArray()[5]
                                + tempContentsStatistics.getReportTypeDelNumArray()[6]
                );
                newResultList.add(tempContentsStatistics);
            }
        }
        return newResultList;
    }

    StatisticsListsHolder findStatisticsLists(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        statisticsListsHolder.getSheetNameList().add("statistics2");
        //1, find reportType statistics
        statisticsListsHolder = findReportTypeStatistics(statisticsListsHolder, parametersMap);
        //2, find category statistics
        statisticsListsHolder = findCategoryStatistics(statisticsListsHolder, parametersMap);
        //3, find contents simple statistics
        statisticsListsHolder = findContentsSimpleStatistics(statisticsListsHolder, parametersMap);
        return statisticsListsHolder;
    }

    StatisticsListsHolder findReportTypeStatistics(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT cr.report_type,(COUNT(distinct chh.comment_id)-(select count(id) from COMMENT_REPORT where report_type = cr.report_type AND handle_result='PASS' ");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append(")) delete_count,  (select count(id) from COMMENT_REPORT where report_type = cr.report_type ");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append(") report_count ");
        listSb.append("FROM COMMENT_HANDLE_HISTORY chh left join COMMENT_REPORT cr on chh.comment_id = cr.comment_id and chh.handle_result = cr.handle_result WHERE 1=1");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (chh.cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND chh.cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND chh.cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append(" GROUP BY report_type");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        Integer RT999DeleteCount = 0;
        Integer RT999ReportCount = 0;
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            if (obj[0] != null && !obj[0].toString().equals("RT999")) {
                statisticsListsHolder.getReportTypeStringList().add(obj[0].toString());
                if (obj[1] != null) {
                    statisticsListsHolder.getDeleteCommentCountForEachReportType().add(Integer.valueOf(obj[1].toString()));
                }
                if (obj[2] != null) {
                    statisticsListsHolder.getReportCountForEachReportType().add(Integer.valueOf(obj[2].toString()));
                }
            } else {
                RT999DeleteCount = RT999DeleteCount + Integer.valueOf(obj[1].toString());
                RT999ReportCount = RT999ReportCount + Integer.valueOf(obj[2].toString());
            }
        }
        if (RT999DeleteCount != 0 || RT999ReportCount != 0) {
            statisticsListsHolder.getReportTypeStringList().add("RT999");
            statisticsListsHolder.getDeleteCommentCountForEachReportType().add(RT999DeleteCount);
            statisticsListsHolder.getReportCountForEachReportType().add(RT999ReportCount);
        }
        for (int i = 0; i < statisticsListsHolder.getReportTypeStringList().size(); i++) {
            if (statisticsListsHolder.getReportTypeStringList().get(i).equals(Const.ReportType.RT100)) {
                statisticsListsHolder.getReportTypeStringList().remove(i);
                statisticsListsHolder.getReportTypeStringList().add(i, "(a)post with slander & slang");
            } else if (statisticsListsHolder.getReportTypeStringList().get(i).equals(Const.ReportType.RT200)) {
                statisticsListsHolder.getReportTypeStringList().remove(i);
                statisticsListsHolder.getReportTypeStringList().add(i, "(b)Illegal Post about gambling, obscene");
            } else if (statisticsListsHolder.getReportTypeStringList().get(i).equals(Const.ReportType.RT300)) {
                statisticsListsHolder.getReportTypeStringList().remove(i);
                statisticsListsHolder.getReportTypeStringList().add(i, "(c)Harmful post for Teenager");
            } else if (statisticsListsHolder.getReportTypeStringList().get(i).equals(Const.ReportType.RT400)) {
                statisticsListsHolder.getReportTypeStringList().remove(i);
                statisticsListsHolder.getReportTypeStringList().add(i, "(d)AD post and promotional post as spam");
            } else if (statisticsListsHolder.getReportTypeStringList().get(i).equals(Const.ReportType.RT500)) {
                statisticsListsHolder.getReportTypeStringList().remove(i);
                statisticsListsHolder.getReportTypeStringList().add(i, "(e)Spam Post");
            } else if (statisticsListsHolder.getReportTypeStringList().get(i).equals(Const.ReportType.RT600)) {
                statisticsListsHolder.getReportTypeStringList().remove(i);
                statisticsListsHolder.getReportTypeStringList().add(i, "(f)repulsion");
            } else if (statisticsListsHolder.getReportTypeStringList().get(i).equals(Const.ReportType.RT999)) {
                statisticsListsHolder.getReportTypeStringList().remove(i);
                statisticsListsHolder.getReportTypeStringList().add(i, "(g)other");
            }

        }
        statisticsListsHolder.getTableTitlesList().add("");
        return statisticsListsHolder;
    }

    StatisticsListsHolder findCategoryStatistics(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT cg.cate_name, count(distinct chh.comment_id) FROM CATEGORY cg left join MG_CATEGORY_CONTENTS mgc on cg.cate_id = mgc.cate_id left join MG_COMMENT mc on mc.contents_id = mgc.contents_id left join COMMENT_HANDLE_HISTORY chh on chh.comment_id = mc.comment_id  WHERE chh.handle_result IN ('HIDD' , 'DEL')");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (chh.cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND chh.cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND chh.cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append("GROUP BY cg.cate_id");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            if (obj[0] != null) {
                statisticsListsHolder.getCategoryNameStringList().add(obj[0].toString());
            } else {
                statisticsListsHolder.getCategoryNameStringList().add("no name");
            }
            if (obj[1] != null) {
                statisticsListsHolder.getDeleteCommentCountForEachCategory().add(Integer.valueOf(obj[1].toString()));
            }
        }
        statisticsListsHolder.getTableTitlesList().add("");
        return statisticsListsHolder;
    }

    StatisticsListsHolder findContentsSimpleStatistics(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT mcs.title, count(distinct mc.comment_id) FROM COMMENT_HANDLE_HISTORY chh left join MG_COMMENT mc on mc.comment_id = chh.comment_id left join MG_CONTENTS mcs on mcs.contents_id = mc.contents_id where chh.handle_result IN ('HIDD' , 'DEL') ");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (chh.cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND chh.cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND chh.cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append("GROUP BY mcs.contents_id");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            if (obj[0] != null) {
                statisticsListsHolder.getContentsTitleStringList().add(obj[0].toString());
            } else {
                statisticsListsHolder.getContentsTitleStringList().add("no name");
            }
            if (obj[1] != null) {
                statisticsListsHolder.getDeleteCommentCountForEachContents().add(Integer.valueOf(obj[1].toString()));
            }
        }
        statisticsListsHolder.getTableTitlesList().add("");
        return statisticsListsHolder;
    }

    StatisticsListsHolder findPerDayTimeLineStatisticsLists(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT  DATE(mc.cdate), HOUR(mc.cdate), GROUP_CONCAT(cr.report_type) report_types ");
        listSb.append("FROM COMMENT_HANDLE_HISTORY chh LEFT JOIN MG_COMMENT mc ON chh.comment_id = mc.comment_id LEFT JOIN COMMENT_REPORT cr ON cr.comment_id = chh.comment_id ");
        listSb.append("WHERE chh.handle_result IN ('HIDD' , 'DEL')");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (mc.cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND mc.cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND mc.cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append("GROUP BY DATE(mc.cdate), HOUR(mc.cdate), mc.comment_id  ORDER BY DATE(mc.cdate) , HOUR(mc.cdate)");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        List<TimeLineStatistics> resultList = new ArrayList<TimeLineStatistics>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            TimeLineStatistics timeLineStatistics = new TimeLineStatistics();
            if (obj[0] != null) {
                timeLineStatistics.setInputDate(obj[0].toString());
            }
            if (obj[1] != null) {
                timeLineStatistics.setInputHour(obj[1].toString());
            }
            if (obj[2] != null) {
                timeLineStatistics.setReportTypes(obj[2].toString());
            }
            resultList.add(timeLineStatistics);
        }
        statisticsListsHolder.setPerDayTimeLineStatisticsList(setReportTypeDelNumArrayForPerDayTimeLineStatisticsList(resultList));
        return statisticsListsHolder;
    }

    StatisticsListsHolder findWholePeriodTimeLineStatisticsLists(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        StringBuilder listSb = new StringBuilder();
        listSb.append("SELECT  HOUR(mc.cdate), GROUP_CONCAT(cr.report_type) report_types ");
        listSb.append("FROM COMMENT_HANDLE_HISTORY chh LEFT JOIN MG_COMMENT mc ON chh.comment_id = mc.comment_id LEFT JOIN COMMENT_REPORT cr ON cr.comment_id = chh.comment_id ");
        listSb.append("WHERE chh.handle_result IN ('HIDD' , 'DEL') ");
        if (parametersMap != null) {
            if (parametersMap.containsKey("startDate") && parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("startDate")) && checkDate((String) parametersMap.get("endDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND (mc.cdate BETWEEN '");
                    listSb.append(startDate);
                    listSb.append("' AND  '");
                    listSb.append(endDate);
                    listSb.append("')");
                }
            } else if (parametersMap.containsKey("startDate")) {
                if (checkDate((String) parametersMap.get("startDate"))) {
                    String startDate = (String) parametersMap.get("startDate");
                    listSb.append(" AND mc.cdate >= '");
                    listSb.append(startDate);
                    listSb.append("' ");
                }
            } else if (parametersMap.containsKey("endDate")) {
                if (checkDate((String) parametersMap.get("endDate"))) {
                    String endDate = (String) parametersMap.get("endDate");
                    listSb.append(" AND mc.cdate <= '");
                    listSb.append(endDate);
                    listSb.append("' ");
                }
            }
        }
        listSb.append(" GROUP BY DATE(mc.cdate), HOUR(mc.cdate), mc.comment_id  ORDER BY HOUR(mc.cdate)");
        Query listQuery = entityManager.createNativeQuery(listSb.toString());
        List objecArraytList = listQuery.getResultList();
        List<TimeLineStatistics> resultList = new ArrayList<TimeLineStatistics>();
        for (int i = 0; i < objecArraytList.size(); i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            TimeLineStatistics timeLineStatistics = new TimeLineStatistics();
            if (obj[0] != null) {
                timeLineStatistics.setInputHour(obj[0].toString());
            }
            if (obj[1] != null) {
                timeLineStatistics.setReportTypes(obj[1].toString());
            }
            resultList.add(timeLineStatistics);
        }
        statisticsListsHolder.setWholePeriodTimeLineStatisticsList(setReportTypeDelNumArrayForWholePeriodTimeLineStatisticsList(resultList));
        return statisticsListsHolder;
    }

    List<TimeLineStatistics> setReportTypeDelNumArrayForPerDayTimeLineStatisticsList(List<TimeLineStatistics> resultList) {
        List<TimeLineStatistics> newResultList = new ArrayList<TimeLineStatistics>();
        TimeLineStatistics tempTimeLineStatistics = null;
        TimeLineStatistics tempPrevTimeLineStatistics = null;
        for (int i = 0; i < resultList.size(); i++) {
            TimeLineStatistics timeLineStatistics = resultList.get(i);
            if (tempTimeLineStatistics == null) {
                tempTimeLineStatistics = timeLineStatistics;
            } else if (!tempTimeLineStatistics.getInputDate().equals(timeLineStatistics.getInputDate())
                    || !tempTimeLineStatistics.getInputHour().equals(timeLineStatistics.getInputHour())) {
//                if (newResultList.size() == 0) {
//                    for (int j = 0; j < Integer.valueOf(tempTimeLineStatistics.getInputHour()); j++) {
//                        TimeLineStatistics ts = new TimeLineStatistics();
//                        ts.setInputDate(tempTimeLineStatistics.getInputDate());
//                        ts.setInputHour(String.valueOf(j));
//                        newResultList.add(ts);
//                    }
//                } else if (newResultList.size() > 0) {
//                    for (int j = Integer.valueOf(tempPrevTimeLineStatistics.getInputHour()) + 1;
//                         j < 24; j++) {
//                        TimeLineStatistics ts = new TimeLineStatistics();
//                        ts.setInputDate(tempPrevTimeLineStatistics.getInputDate());
//                        ts.setInputHour(String.valueOf(j));
//                        newResultList.add(ts);
//                    }
//                    for (int j = 0; j < Integer.valueOf(tempTimeLineStatistics.getInputHour()); j++) {
//                        TimeLineStatistics ts = new TimeLineStatistics();
//                        ts.setInputDate(tempTimeLineStatistics.getInputDate());
//                        ts.setInputHour(String.valueOf(j));
//                        newResultList.add(ts);
//                    }
//                }
                tempTimeLineStatistics.setDeleteCommentCount(tempTimeLineStatistics.getReportTypeDelNumArray()[0]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[1]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[2]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[3]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[4]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[5]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[6]
                );
                newResultList.add(tempTimeLineStatistics);
                tempPrevTimeLineStatistics = tempTimeLineStatistics;
                tempTimeLineStatistics = timeLineStatistics;
            }

            if (timeLineStatistics.getReportTypes() == null) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[6] = tempTimeLineStatistics.getReportTypeDelNumArray()[6] + 1;
            } else if (timeLineStatistics.getReportTypes().contains(Const.ReportType.RT100)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[0] = tempTimeLineStatistics.getReportTypeDelNumArray()[0] + 1;
            } else if (timeLineStatistics.getReportTypes().contains(Const.ReportType.RT200)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[1] = tempTimeLineStatistics.getReportTypeDelNumArray()[1] + 1;
            } else if (timeLineStatistics.getReportTypes().contains(Const.ReportType.RT300)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[2] = tempTimeLineStatistics.getReportTypeDelNumArray()[2] + 1;
            } else if (timeLineStatistics.getReportTypes().contains(Const.ReportType.RT400)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[3] = tempTimeLineStatistics.getReportTypeDelNumArray()[3] + 1;
            } else if (timeLineStatistics.getReportTypes().contains(Const.ReportType.RT500)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[4] = tempTimeLineStatistics.getReportTypeDelNumArray()[4] + 1;
            } else if (timeLineStatistics.getReportTypes().contains(Const.ReportType.RT600)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[5] = tempTimeLineStatistics.getReportTypeDelNumArray()[5] + 1;
            } else if (timeLineStatistics.getReportTypes().contains(Const.ReportType.RT999)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[6] = tempTimeLineStatistics.getReportTypeDelNumArray()[6] + 1;
            }

            if (i == resultList.size() - 1) {
                tempTimeLineStatistics.setDeleteCommentCount(tempTimeLineStatistics.getReportTypeDelNumArray()[0]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[1]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[2]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[3]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[4]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[5]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[6]
                );
                newResultList.add(tempTimeLineStatistics);
            }
        }
        return newResultList;
    }

    List<TimeLineStatistics> setReportTypeDelNumArrayForWholePeriodTimeLineStatisticsList(List<TimeLineStatistics> resultList) {
        List<TimeLineStatistics> newResultList = new ArrayList<TimeLineStatistics>();
        TimeLineStatistics tempTimeLineStatistics = null;
        String reportTypes = null;
        for (TimeLineStatistics timeLineStatistics : resultList) {
            if (tempTimeLineStatistics == null) {
                tempTimeLineStatistics = timeLineStatistics;
            }
            if (!tempTimeLineStatistics.getInputHour().equals(timeLineStatistics.getInputHour())) {
                tempTimeLineStatistics.setDeleteCommentCount(tempTimeLineStatistics.getReportTypeDelNumArray()[0]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[1]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[2]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[3]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[4]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[5]
                                + tempTimeLineStatistics.getReportTypeDelNumArray()[6]
                );
                newResultList.add(tempTimeLineStatistics);
                tempTimeLineStatistics = timeLineStatistics;
                reportTypes = timeLineStatistics.getReportTypes();
            }
            if (reportTypes == null) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[6] = tempTimeLineStatistics.getReportTypeDelNumArray()[6] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT100)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[0] = tempTimeLineStatistics.getReportTypeDelNumArray()[0] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT200)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[1] = tempTimeLineStatistics.getReportTypeDelNumArray()[1] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT300)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[2] = tempTimeLineStatistics.getReportTypeDelNumArray()[2] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT400)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[3] = tempTimeLineStatistics.getReportTypeDelNumArray()[3] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT500)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[4] = tempTimeLineStatistics.getReportTypeDelNumArray()[4] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT600)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[5] = tempTimeLineStatistics.getReportTypeDelNumArray()[5] + 1;
            } else if (reportTypes.contains(Const.ReportType.RT999)) {
                tempTimeLineStatistics.getReportTypeDelNumArray()[6] = tempTimeLineStatistics.getReportTypeDelNumArray()[6] + 1;
            }
        }
        return newResultList;
    }

    StatisticsListsHolder findTimeLineStatisticsLists(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        statisticsListsHolder.getSheetNameList().add("statistics3");
        statisticsListsHolder = findPerDayTimeLineStatisticsLists(statisticsListsHolder, parametersMap);
        statisticsListsHolder = findWholePeriodTimeLineStatisticsLists(statisticsListsHolder, parametersMap);
        return statisticsListsHolder;
    }

    StatisticsListsHolder findSheet1(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        return findContentsStatisticsList(statisticsListsHolder, parametersMap);
    }

    StatisticsListsHolder findSheet2(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        return findStatisticsLists(statisticsListsHolder, parametersMap);
    }

    StatisticsListsHolder findSheet3(StatisticsListsHolder statisticsListsHolder, HashMap<String, Object> parametersMap) throws Exception {
        return findTimeLineStatisticsLists(statisticsListsHolder, parametersMap);
    }


    @Override
    public HSSFWorkbook findStatistics(String q) throws Exception {
        StatisticsListsHolder statisticsListsHolder = new StatisticsListsHolder();
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
        statisticsListsHolder = findSheet1(statisticsListsHolder, parametersMap);
        statisticsListsHolder = findSheet2(statisticsListsHolder, parametersMap);
        statisticsListsHolder = findSheet3(statisticsListsHolder, parametersMap);

        return ExcelUtil.createStatisticsExcel(statisticsListsHolder, null);
    }

    @Override
    public Integer updateMonitor(Monitor monitor) throws Exception {
        QMonitor qMonitor = QMonitor.monitor;
        JPAUpdateClause QMonitorUpdate = new JPAUpdateClause(entityManager, qMonitor);
        Integer num = 0;
        QMonitorUpdate.where(qMonitor.monitorId.eq(monitor.getMonitorId()));
        if (monitor.getLevel() != null) {
            QMonitorUpdate.set(qMonitor.level, monitor.getLevel());
        }
        if (monitor.getCdate() != null) {
            QMonitorUpdate.set(qMonitor.cdate, monitor.getCdate());
        }
        if (monitor.getName() != null) {
            QMonitorUpdate.set(qMonitor.name, monitor.getName());
        }
        if (monitor.getPassword() != null) {
            QMonitorUpdate.set(qMonitor.password, monitor.getPassword());
        }
        if (monitor.getStatus() != null) {
            QMonitorUpdate.set(qMonitor.status, monitor.getStatus());
        }
        if (monitor.getUdate() != null) {
            QMonitorUpdate.set(qMonitor.udate, monitor.getUdate());
        }
        num = Long.valueOf(QMonitorUpdate.execute()).intValue();
        return num;
    }
}
