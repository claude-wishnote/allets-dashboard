package com.allets.backend.dashboard.server.data.result;

import java.util.Date;

/**
 * Created by pikicast on 17/5/3.
 */
public class UserStatisticsResult {
    private Date cdate;
    private Integer result;
    private String type;

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserSatisticsResult{" +
                "cdate=" + cdate +
                ", result=" + result +
                ", type='" + type + '\'' +
                '}';
    }
}
