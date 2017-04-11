package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import java.util.List;

/**
 * Created by claude on 2015/9/23.
 */
@ApiObject(name = "AlertResult", description = "alert result for write/read in redis")
public class AlertResult {
    @ApiObjectField(description = "alert count")
    Integer alert_count;
    @ApiObjectField(description = "alert message queue")
    List<String> alert_queue;

    public Integer getAlert_count() {
        return alert_count;
    }

    public void setAlert_count(Integer alert_count) {
        this.alert_count = alert_count;
    }

    public List<String> getAlert_queue() {
        return alert_queue;
    }

    public void setAlert_queue(List<String> alert_queue) {
        this.alert_queue = alert_queue;
    }

    @Override
    public String toString() {
        return "AlertResult{" +
                "alert_count=" + alert_count +
                ", alert_queue=" + alert_queue +
                '}';
    }
}
