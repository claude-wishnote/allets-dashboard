package com.allets.backend.data.server.data.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by claude on 2016/3/18.
 */
public class StatisticsListsHolder {
    private List<String> sheetNameList;
    private List<String> tableTitlesList;

    private List<String> reportTypeStringList;
    private List<Integer> deleteCommentCountForEachReportType;
    private List<Integer> reportCountForEachReportType;

    private List<String> categoryNameStringList;
    private List<Integer> deleteCommentCountForEachCategory;

    private List<String> contentsTitleStringList;
    private List<Integer> deleteCommentCountForEachContents;

    private List<ContentsStatistics> contentsStatisticsList;

    private List<TimeLineStatistics> perDayTimeLineStatisticsList;
    private List<TimeLineStatistics> wholePeriodTimeLineStatisticsList;


    public List<String> getSheetNameList() {
        return sheetNameList;
    }

    public void setSheetNameList(List<String> sheetNameList) {
        this.sheetNameList = sheetNameList;
    }

    public List<String> getTableTitlesList() {
        return tableTitlesList;
    }

    public void setTableTitlesList(List<String> tableTitlesList) {
        this.tableTitlesList = tableTitlesList;
    }

    public List<String> getReportTypeStringList() {
        return reportTypeStringList;
    }

    public void setReportTypeStringList(List<String> reportTypeStringList) {
        this.reportTypeStringList = reportTypeStringList;
    }

    public List<Integer> getDeleteCommentCountForEachReportType() {
        return deleteCommentCountForEachReportType;
    }

    public void setDeleteCommentCountForEachReportType(List<Integer> deleteCommentCountForEachReportType) {
        this.deleteCommentCountForEachReportType = deleteCommentCountForEachReportType;
    }

    public List<Integer> getReportCountForEachReportType() {
        return reportCountForEachReportType;
    }

    public void setReportCountForEachReportType(List<Integer> reportCountForEachReportType) {
        this.reportCountForEachReportType = reportCountForEachReportType;
    }

    public List<String> getCategoryNameStringList() {
        return categoryNameStringList;
    }

    public void setCategoryNameStringList(List<String> categoryNameStringList) {
        this.categoryNameStringList = categoryNameStringList;
    }

    public List<Integer> getDeleteCommentCountForEachCategory() {
        return deleteCommentCountForEachCategory;
    }

    public void setDeleteCommentCountForEachCategory(List<Integer> deleteCommentCountForEachCategory) {
        this.deleteCommentCountForEachCategory = deleteCommentCountForEachCategory;
    }

    public List<String> getContentsTitleStringList() {
        return contentsTitleStringList;
    }

    public void setContentsTitleStringList(List<String> contentsTitleStringList) {
        this.contentsTitleStringList = contentsTitleStringList;
    }

    public List<Integer> getDeleteCommentCountForEachContents() {
        return deleteCommentCountForEachContents;
    }

    public void setDeleteCommentCountForEachContents(List<Integer> deleteCommentCountForEachContents) {
        this.deleteCommentCountForEachContents = deleteCommentCountForEachContents;
    }

    public List<ContentsStatistics> getContentsStatisticsList() {
        return contentsStatisticsList;
    }

    public void setContentsStatisticsList(List<ContentsStatistics> contentsStatisticsList) {
        this.contentsStatisticsList = contentsStatisticsList;
    }

    public List<TimeLineStatistics> getPerDayTimeLineStatisticsList() {
        return perDayTimeLineStatisticsList;
    }

    public void setPerDayTimeLineStatisticsList(List<TimeLineStatistics> perDayTimeLineStatisticsList) {
        this.perDayTimeLineStatisticsList = perDayTimeLineStatisticsList;
    }

    public List<TimeLineStatistics> getWholePeriodTimeLineStatisticsList() {
        return wholePeriodTimeLineStatisticsList;
    }

    public void setWholePeriodTimeLineStatisticsList(List<TimeLineStatistics> wholePeriodTimeLineStatisticsList) {
        this.wholePeriodTimeLineStatisticsList = wholePeriodTimeLineStatisticsList;
    }

    public StatisticsListsHolder() {
        this.sheetNameList = new ArrayList<String>();
        this.tableTitlesList = new ArrayList<String>();
        this.reportTypeStringList = new ArrayList<String>();
        this.deleteCommentCountForEachReportType = new ArrayList<Integer>();
        this.reportCountForEachReportType = new ArrayList<Integer>();
        this.categoryNameStringList = new ArrayList<String>();
        this.deleteCommentCountForEachCategory = new ArrayList<Integer>();
        this.contentsTitleStringList = new ArrayList<String>();
        this.deleteCommentCountForEachContents = new ArrayList<Integer>();
        this.contentsStatisticsList = new ArrayList<ContentsStatistics>();
        this.perDayTimeLineStatisticsList = new ArrayList<TimeLineStatistics>();
        this.wholePeriodTimeLineStatisticsList = new ArrayList<TimeLineStatistics>();
    }

    public StatisticsListsHolder(List<String> sheetNameList, List<String> tableTitlesList, List<String> reportTypeStringList, List<Integer> deleteCommentCountForEachReportType, List<Integer> reportCountForEachReportType, List<String> categoryNameStringList, List<Integer> deleteCommentCountForEachCategory, List<String> contentsTitleStringList, List<Integer> deleteCommentCountForEachContents, List<ContentsStatistics> contentsStatisticsList, List<TimeLineStatistics> perDayTimeLineStatisticsList, List<TimeLineStatistics> wholePeriodTimeLineStatisticsList) {
        this.sheetNameList = sheetNameList;
        this.tableTitlesList = tableTitlesList;
        this.reportTypeStringList = reportTypeStringList;
        this.deleteCommentCountForEachReportType = deleteCommentCountForEachReportType;
        this.reportCountForEachReportType = reportCountForEachReportType;
        this.categoryNameStringList = categoryNameStringList;
        this.deleteCommentCountForEachCategory = deleteCommentCountForEachCategory;
        this.contentsTitleStringList = contentsTitleStringList;
        this.deleteCommentCountForEachContents = deleteCommentCountForEachContents;
        this.contentsStatisticsList = contentsStatisticsList;
        this.perDayTimeLineStatisticsList = perDayTimeLineStatisticsList;
        this.wholePeriodTimeLineStatisticsList = wholePeriodTimeLineStatisticsList;
    }
}
