package com.allets.backend.data.server.security;

import com.allets.backend.data.server.exception.ForbiddenException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


public class ShiroUtils {

    public static void login(HttpServletResponse response, final AuthenticationToken token) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        //
        addSessionIdHeader(response);
    }

    public static void login(HttpServletResponse response, final String username, final String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        //
        addSessionIdHeader(response);
    }

    public static void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    public static String getSessionId() {
        return Objects.toString(SecurityUtils.getSubject().getSession().getId(), "");
    }

    public static void addSessionIdHeader(HttpServletResponse response) {
        response.setHeader(ShiroWebSessionManager.HTTP_HEADER_SESSION_KEY, getSessionId());
    }

    public static void responseForbidden() {
        throw new ForbiddenException();
    }


}
