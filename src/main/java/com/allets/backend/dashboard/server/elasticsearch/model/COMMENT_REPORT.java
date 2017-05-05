package com.allets.backend.dashboard.server.elasticsearch.model;

import java.util.Date;

/**
 * Created by claude on 2016/1/12.
 */
public class COMMENT_REPORT {
    private Long comment_id;
    private String text;
    private String user_name;
    private String user_email;
    private Long report_id;
    private Date report_cdate;

    public COMMENT_REPORT(Long comment_id, String text, String user_name, String user_email, Long report_id, Date report_cdate) {
        this.comment_id = comment_id;
        this.text = text;
        this.user_name = user_name;
        this.user_email = user_email;
        this.report_id = report_id;
        this.report_cdate = report_cdate;
    }

    public COMMENT_REPORT() {
        super();
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Long getReport_id() {
        return report_id;
    }

    public void setReport_id(Long report_id) {
        this.report_id = report_id;
    }

    public Date getReport_cdate() {
        return report_cdate;
    }

    public void setReport_cdate(Date report_cdate) {
        this.report_cdate = report_cdate;
    }

    @Override
    public String toString() {
        return "COMMENT_REPORT{" +
                "comment_id=" + comment_id +
                ", text='" + text + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", report_id=" + report_id +
                ", report_cdate=" + report_cdate +
                '}';
    }
}
