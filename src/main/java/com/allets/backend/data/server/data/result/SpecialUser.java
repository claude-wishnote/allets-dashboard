package com.allets.backend.data.server.data.result;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by claude on 2015/10/16.
 */
@ApiObject(name = "SpecialUser", description = "special users")
public class SpecialUser {
    @ApiObjectField(description = "uid")
    Long uid;
    @ApiObjectField(description = "name")
    String name;
    @ApiObjectField(description = "photo")
    String photo;
    @ApiObjectField(description = "text")
    String text;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "SpecialUser{" +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
