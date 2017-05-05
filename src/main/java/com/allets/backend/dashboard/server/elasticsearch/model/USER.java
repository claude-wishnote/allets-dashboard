package com.allets.backend.dashboard.server.elasticsearch.model;

import java.util.Date;

/**
 * Created by claude on 2016/5/11.
 */
public class USER {
    private Long uid;
    private String name;
    private String email;
    private Date udate;

    public USER(Long uid, String name, String email, Date udate) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.udate = udate;
    }

    public USER() {
        super();
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "USER{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", udate=" + udate +
                '}';
    }
}
