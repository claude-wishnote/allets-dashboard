package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by claude on 2016/1/24.
 */
@ApiObject(name = "MgCardResult", description = "MgCard Result")
public class MgCardResult {
    @ApiObjectField(description = "cardId")
    Long cardId;
    @ApiObjectField(description = "url")
    String url;
    @ApiObjectField(description = "cardType")
    String cardType;
    @ApiObjectField(description = "ordering")
    Integer ordering;
    @ApiObjectField(description = "title")
    String title;
    @ApiObjectField(description = "description")
    String description;
    @ApiObjectField(description = "contentsId")
    Long contentsId;
    @ApiObjectField(description = "videoThumbnailUrl")
    String videoThumbnailUrl;

    public MgCardResult() {
        super();
    }

    public MgCardResult(Long cardId, String url, String cardType, Integer ordering, String title, String description, Long contentsId, String videoThumbnailUrl) {
        this.cardId = cardId;
        this.url = url;
        this.cardType = cardType;
        this.ordering = ordering;
        this.title = title;
        this.description = description;
        this.contentsId = contentsId;
        this.videoThumbnailUrl = videoThumbnailUrl;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getContentsId() {
        return contentsId;
    }

    public void setContentsId(Long contentsId) {
        this.contentsId = contentsId;
    }

    public String getVideoThumbnailUrl() {
        return videoThumbnailUrl;
    }

    public void setVideoThumbnailUrl(String videoThumbnailUrl) {
        this.videoThumbnailUrl = videoThumbnailUrl;
    }

    @Override
    public String toString() {
        return "MgCardResult{" +
                "cardId=" + cardId +
                ", url='" + url + '\'' +
                ", cardType='" + cardType + '\'' +
                ", ordering='" + ordering + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", contentsId='" + contentsId + '\'' +
                ", videoThumbnailUrl='" + videoThumbnailUrl + '\'' +
                '}';
    }
}
