package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by claude on 2015/9/11.
 */
@ApiObject(name = "ReportTypeCountResult", description = "report type count")
public class ReportTypeCountResult {

    @ApiObjectField(description = "rt100 count")
    private int rt100Count;
    @ApiObjectField(description = "rt200 count")
    private int rt200Count;
    @ApiObjectField(description = "rt300 count")
    private int rt300Count;
    @ApiObjectField(description = "rt400 count")
    private int rt400Count;
    @ApiObjectField(description = "rt500 count")
    private int rt500Count;
    @ApiObjectField(description = "rt600 count")
    private int rt600Count;
    @ApiObjectField(description = "rt999 count")
    private int rt999Count;

    public int getRt100Count() {
        return rt100Count;
    }

    public void setRt100Count(int rt100Count) {
        this.rt100Count = rt100Count;
    }

    public int getRt200Count() {
        return rt200Count;
    }

    public void setRt200Count(int rt200Count) {
        this.rt200Count = rt200Count;
    }

    public int getRt300Count() {
        return rt300Count;
    }

    public void setRt300Count(int rt300Count) {
        this.rt300Count = rt300Count;
    }

    public int getRt400Count() {
        return rt400Count;
    }

    public void setRt400Count(int rt400Count) {
        this.rt400Count = rt400Count;
    }

    public int getRt500Count() {
        return rt500Count;
    }

    public void setRt500Count(int rt500Count) {
        this.rt500Count = rt500Count;
    }

    public int getRt600Count() {
        return rt600Count;
    }

    public void setRt600Count(int rt600Count) {
        this.rt600Count = rt600Count;
    }

    public int getRt999Count() {
        return rt999Count;
    }

    public void setRt999Count(int rt999Count) {
        this.rt999Count = rt999Count;
    }
}
