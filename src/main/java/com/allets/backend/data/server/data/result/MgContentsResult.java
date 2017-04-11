package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by claude on 2016/1/24.
 */
@ApiObject(name = "MgContentsResult", description = "MgContents Result")
public class MgContentsResult {

    @ApiObjectField(description = "contentsId")
    Long  contentsId;

    @ApiObjectField(description = "title")
    String title;

    @ApiObjectField(description = "contentsType")
    String  contentsType;

    public MgContentsResult() {
        super();
    }

    public MgContentsResult(Long contentsId, String title, String contentsType) {
        this.contentsId = contentsId;
        this.title = title;
        this.contentsType = contentsType;
    }

    public Long getContentsId() {
        return contentsId;
    }

    public void setContentsId(Long contentsId) {
        this.contentsId = contentsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentsType() {
        return contentsType;
    }

    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    @Override
    public String toString() {
        return "MgContentsResult{" +
                "contentsId=" + contentsId +
                ", title='" + title + '\'' +
                ", contentsType='" + contentsType + '\'' +
                '}';
    }
}
