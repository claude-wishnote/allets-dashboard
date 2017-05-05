package com.allets.backend.dashboard.server.facade.impl;

import com.allets.backend.dashboard.server.consts.Const;
import com.allets.backend.dashboard.server.data.result.UserResult;
import com.allets.backend.dashboard.server.data.result.UserStatisticsResult;
import com.allets.backend.dashboard.server.exception.NotFoundUserException;
import com.allets.backend.dashboard.server.facade.UserFacade;
import com.allets.backend.dashboard.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class UserFacadeImpl implements UserFacade {

    @Autowired
    UserService userService;

    public Page<UserResult> findAllUser(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userService.seletctAllUsers(offset, limit, q);
        if (page == null) {
            throw new NotFoundUserException();
        }
//        if (page.getContent().size() == 1) {
//            page.getContent().get(0).setShareCount(findShareCount(page.getContent().get(0).getUid()));
//            page.getContent().get(0).setAlertCount(findAlertCount(page.getContent().get(0).getUid()));
//        }
        return page;
    }

     public Page<UserResult> findAllUserSimple(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userService.seletctAllUsersSimple(offset, limit, q);
        if (page == null) {
            throw new NotFoundUserException();
        }
        return page;
    }

    @Override
    public Page<UserResult> findUserList(String action, Integer offset, Integer limit, String q, String sort) throws Exception {
        Page<UserResult> pageResult = new PageImpl<UserResult>(new ArrayList<UserResult>(), new PageRequest(0, 1), 0);
        if (Const.Action.ALL.equals(action)) {
            pageResult = findAllUser(Integer.valueOf(offset), Integer.valueOf(limit), q);
        } else if (Const.Action.ALLSIMPLE.equals(action)) {
            pageResult = findAllUserSimple(Integer.valueOf(offset), Integer.valueOf(limit), q);
        }
        return pageResult;
    }

    @Override
    public List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception {
        return userService.findUserStatisticsResult(q);
    }
}
