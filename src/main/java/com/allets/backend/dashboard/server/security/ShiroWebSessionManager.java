package com.allets.backend.dashboard.server.security;

import com.google.common.base.Strings;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.UUID;

public class ShiroWebSessionManager extends DefaultWebSessionManager {

    public static final String HTTP_HEADER_SESSION_KEY = "X-ALLETS-TOKEN";
    private static Logger log = LoggerFactory.getLogger(ShiroWebSessionManager.class);

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        if (request instanceof ShiroHttpServletRequest) {
            ShiroHttpServletRequest shsr = (ShiroHttpServletRequest) request;
            final String token = shsr.getHeader(HTTP_HEADER_SESSION_KEY);
            if (Strings.isNullOrEmpty(token)) {
                return UUID.randomUUID().toString();//SecurityUtils.getSubject().getSession().getId();
            } else {
                return token;
            }
        } else {
            log.warn("UNEXPECTED TYPED REQUEST!!!");
            return null;
        }
    }
}
