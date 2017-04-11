package com.allets.backend.data.server.utils;

/**
 * Created by clay on 2015. 7. 21..
 */
public enum HttpStatusCode {

    SC_200(200),
    SC_201(201),
    SC_400(400),
    SC_401(401),
    SC_403(403),
    SC_404(404),
    SC_410(410),
    SC_500(500)
    ;

    private final int value;

    private HttpStatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
