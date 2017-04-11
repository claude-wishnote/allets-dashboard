package com.allets.backend.data.server.elasticsearch.model;

import java.util.Date;

/**
 * Created by claude on 2015/12/8.
 */
public class MG_COMMENT {
    private Long comment_id;
    private String text;
    private Date cdate;

    private Long report_id;
    private Date report_cdate;

    private String user_name;
    private String user_email;

    private Long handle_id;
    private String handle_result;

    public MG_COMMENT(Long comment_id, String text, Date cdate, Long report_id, String handle_result, Date report_cdate, String user_name, String user_email, Long handle_id) {
        this.comment_id = comment_id;
        this.text = text;
        this.cdate = cdate;
        this.report_id = report_id;
        this.handle_result = handle_result;
        this.report_cdate = report_cdate;
        this.user_name = user_name;
        this.user_email = user_email;
        this.handle_id = handle_id;
    }

    public MG_COMMENT() {
        super();
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
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

    public Long getReport_id() {
        return report_id;
    }

    public void setReport_id(Long report_id) {
        this.report_id = report_id;
    }

    public String getHandle_result() {
        return handle_result;
    }

    public void setHandle_result(String handle_result) {
        this.handle_result = handle_result;
    }

    public Date getReport_cdate() {
        return report_cdate;
    }

    public void setReport_cdate(Date report_cdate) {
        this.report_cdate = report_cdate;
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

    public Long getHandle_id() {
        return handle_id;
    }

    public void setHandle_id(Long handle_id) {
        this.handle_id = handle_id;
    }

    @Override
    public String toString() {
        return "MG_COMMENT{" +
                "comment_id=" + comment_id +
                ", text='" + text + '\'' +
                ", cdate=" + cdate +
                ", report_id=" + report_id +
                ", report_cdate=" + report_cdate +
                ", user_name='" + user_name + '\'' +
                ", user_email='" + user_email + '\'' +
                ", handle_id=" + handle_id +
                ", handle_result='" + handle_result + '\'' +
                '}';
    }
}
