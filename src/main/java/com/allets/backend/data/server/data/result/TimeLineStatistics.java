package com.allets.backend.data.server.data.result;

import java.util.Arrays;

/**
 * Created by claude on 2016/4/10.
 */
public class TimeLineStatistics {

    private String inputDate;
    private String inputHour;
    private Integer deleteCommentCount = 0;
    private String reportTypes;
    private Integer[] ReportTypeDelNumArray = {0, 0, 0, 0, 0, 0, 0};

    public TimeLineStatistics() {
        super();
    }

    public TimeLineStatistics(String inputDate, String inputHour, Integer deleteCommentCount, String reportTypes, Integer[] reportTypeDelNumArray) {
        this.inputDate = inputDate;
        this.inputHour = inputHour;
        this.deleteCommentCount = deleteCommentCount;
        this.reportTypes = reportTypes;
        ReportTypeDelNumArray = reportTypeDelNumArray;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getInputHour() {
        return inputHour;
    }

    public void setInputHour(String inputHour) {
        this.inputHour = inputHour;
    }

    public Integer getDeleteCommentCount() {
        return deleteCommentCount;
    }

    public void setDeleteCommentCount(Integer deleteCommentCount) {
        this.deleteCommentCount = deleteCommentCount;
    }

    public Integer[] getReportTypeDelNumArray() {
        return ReportTypeDelNumArray;
    }

    public void setReportTypeDelNumArray(Integer[] reportTypeDelNumArray) {
        ReportTypeDelNumArray = reportTypeDelNumArray;
    }

    public String getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(String reportTypes) {
        this.reportTypes = reportTypes;
    }

    @Override
    public String toString() {
        return "TimeLineStatistics{" +
                "inputDate='" + inputDate + '\'' +
                ", inputHour='" + inputHour + '\'' +
                ", deleteCommentCount='" + deleteCommentCount + '\'' +
                ", reportTypes='" + reportTypes + '\'' +
                ", ReportTypeDelNumArray=" + Arrays.toString(ReportTypeDelNumArray) +
                '}';
    }
}
