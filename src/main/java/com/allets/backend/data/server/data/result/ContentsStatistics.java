package com.allets.backend.data.server.data.result;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by claude on 2016/2/26.
 */
public class ContentsStatistics {
    private String handleDateString;
    private String contentName;
    private Date publishDate;
    private Integer deleteCommentNumber;
    private String inHouseAccounts;
    private String category;
    private Long contentsId;

    private String reportTypes;
    private Integer[] ReportTypeDelNumArray = {0, 0, 0, 0, 0, 0, 0};

    public ContentsStatistics() {
        super();
    }

    public ContentsStatistics(String handleDateString, String contentName, Date publishDate, Integer deleteCommentNumber, String inHouseAccounts, String category, Long contentsId, String reportTypes, Integer[] reportTypeDelNumArray) {
        this.handleDateString = handleDateString;
        this.contentName = contentName;
        this.publishDate = publishDate;
        this.deleteCommentNumber = deleteCommentNumber;
        this.inHouseAccounts = inHouseAccounts;
        this.category = category;
        this.contentsId = contentsId;
        this.reportTypes = reportTypes;
        ReportTypeDelNumArray = reportTypeDelNumArray;
    }

    public String getHandleDateString() {
        return handleDateString;
    }

    public void setHandleDateString(String handleDateString) {
        this.handleDateString = handleDateString;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getDeleteCommentNumber() {
        return deleteCommentNumber;
    }

    public void setDeleteCommentNumber(Integer deleteCommentNumber) {
        this.deleteCommentNumber = deleteCommentNumber;
    }

    public String getInHouseAccounts() {
        return inHouseAccounts;
    }

    public void setInHouseAccounts(String inHouseAccounts) {
        this.inHouseAccounts = inHouseAccounts;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getContentsId() {
        return contentsId;
    }

    public void setContentsId(Long contentsId) {
        this.contentsId = contentsId;
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
        return "ContentsStatistics{" +
                "handleDateString='" + handleDateString + '\'' +
                ", contentName='" + contentName + '\'' +
                ", publishDate=" + publishDate +
                ", deleteCommentNumber=" + deleteCommentNumber +
                ", inHouseAccounts='" + inHouseAccounts + '\'' +
                ", category='" + category + '\'' +
                ", contentsId=" + contentsId +
                ", reportTypes='" + reportTypes + '\'' +
                ", ReportTypeDelNumArray=" + Arrays.toString(ReportTypeDelNumArray) +
                '}';
    }
}
