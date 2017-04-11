package com.allets.backend.data.server.security;

import com.allets.backend.data.server.consts.Status;
import com.allets.backend.data.server.exception.UnAuthorizedException;
import com.allets.backend.data.server.repository.common.MonitorRepository;
import com.allets.backend.data.server.entity.common.Monitor;
import com.allets.backend.data.server.consts.Level;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

@Component
public class ShiroSecurityRealm extends AuthorizingRealm {

    public static final Logger log = LoggerFactory.getLogger(ShiroSecurityRealm.class);

    private MonitorRepository monitorRepository;

    public ShiroSecurityRealm() {
    }

    public ShiroSecurityRealm(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return usernamePasswordAuthorizationInfo(principals);
    }


    private AuthorizationInfo usernamePasswordAuthorizationInfo(PrincipalCollection principals) {
        if (monitorRepository == null) {
            WebApplicationContext webApplication = ContextLoader.getCurrentWebApplicationContext();
            monitorRepository = (MonitorRepository) webApplication.getBean("monitorRepository");
        }
        final String USERNAME = ObjectUtils.toString(principals.getPrimaryPrincipal());
        Monitor monitor = monitorRepository.findByName(USERNAME);
        if (Status.MonitorStatus.HOLD.equals(monitor.getStatus())) {
            throw new UnAuthorizedException();
        }
        return authorizationInfoByMonitor(monitor);
    }

    private AuthorizationInfo authorizationInfoByMonitor(Monitor monitor) {
        if (monitor == null) {
            return null;
        } else {
            SimpleAuthorizationInfo a = new SimpleAuthorizationInfo();
            a.addRole(Level.NORMAL);
            if (StringUtils.equals(Level.ADMIN, monitor.getLevel())) {
                a.addRole(Level.ADMIN);
            }
            return a;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException, UnAuthorizedException {
        return authenUsernamePassword((UsernamePasswordToken) token);
    }


    private AuthenticationInfo authenUsernamePassword(UsernamePasswordToken token) {

        if (monitorRepository == null) {
            WebApplicationContext webApplication = ContextLoader.getCurrentWebApplicationContext();
            monitorRepository = (MonitorRepository) webApplication.getBean("monitorRepository");
        }

        final String USERNAME = token.getUsername();
        Monitor monitor = monitorRepository.findByName(USERNAME);
        if (monitor == null) {
            log.error("Identifier doesn't exist.");
            throw new UnAuthorizedException();
        } else if (Status.MonitorStatus.HOLD.equals(monitor.getStatus())) {
            log.error("This User is blocked.");
            throw new UnAuthorizedException();
        }

        String encodedPassword = String.copyValueOf(token.getPassword());

        boolean isPasswordEqual = StringUtils.equals(monitor.getPassword(), encodedPassword);
        if (isPasswordEqual == false) {
            log.error("Invalid credential.");
            throw new UnAuthorizedException();
        }
        //
        SimpleAuthenticationInfo account = new SimpleAuthenticationInfo(
                token.getPrincipal(), token.getPassword(), this.getName());
        return account;
    }
}
