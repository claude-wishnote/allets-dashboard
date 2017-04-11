package com.allets.backend.data.server.utils;

/**
 * Created by clay on 2015. 7. 21..
 */
public enum HttpStatusMessage {

    //200
    SC_200("success"),
    //201
    SC_201("accept"),
    //400
    SC_400("bad syntax"),
    //401
    SC_401("login failure"),
    //403
    SC_403("no permission"),
    //404
    SC_404("not found"),
    //410
    SC_410("gone"),
    //500
    SC_500("system error");

    private String value;

    private HttpStatusMessage(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

}
