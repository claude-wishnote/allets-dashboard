package com.allets.backend.dashboard.server.utils;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by clay on 2015. 7. 21..
 */
public class CookieThenAcceptHeaderLocaleResolver extends CookieLocaleResolver {

    @Override
    protected Locale determineDefaultLocale(HttpServletRequest request) {

        String acceptLanguage = request.getHeader("Accept-Language");
        if (acceptLanguage == null || acceptLanguage.trim().isEmpty()) {
            return super.determineDefaultLocale(request);
        }
        return request.getLocale();
    }

}
