package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.consts.Status;
import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.*;
import com.allets.backend.data.server.entity.common.UserInvalid;
import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.exception.NotSupportActionException;
import com.allets.backend.data.server.facade.UserFacade;
import com.allets.backend.data.server.service.UserHandleHistoryService;
import com.allets.backend.data.server.service.UserReportService;
import com.allets.backend.data.server.service.UserService;
import com.allets.backend.data.server.entity.common.User;
import com.allets.backend.data.server.entity.common.UserBlackList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class UserFacadeImpl implements UserFacade {

    @Autowired
    UserReportService userReportService;
    @Autowired
    UserHandleHistoryService userHandleHistoryService;
    @Autowired
    UserService userService;

    @Override
    public Page<UserResult> findAllReportedUser(Integer offset, Integer limit, String q, String sort) throws Exception {
        //TODO field requirement

        String searchBy = null;
        String keyword = null;
        if (StringUtils.isNotBlank(q)) {
            String dq = URLDecoder.decode(q, "utf-8");
            String[] conditions = dq.split(",");
            if (conditions != null & conditions.length > 0) {
                String[] arr = conditions[0].split("=");
                if (arr != null && arr.length == 2) {
                    searchBy = arr[0];
                    keyword = arr[1];
                }
            }
        }

        return userReportService.selectAllReportedUser(offset, limit, searchBy, keyword, sort);
    }

    @Override
    @Transactional(value = "commonTxManager")
    public UserDTO handleUser(String action, long uid, long monitorId, String q, String defaultName, String blockPath, String deletePath, String blockTitle, String deleteTitle, String passwordEmaiTemplatePath, String passwordEmailTitle) throws Exception {
        if (Const.Action.PASS.equals(action)) {
            return userService.handleUser(uid, monitorId, Status.UserHandleStatus.PASS, blockPath, deletePath, blockTitle, deleteTitle);
        } else if (Const.Action.OUT.equals(action)) {
            //use blok to paser UserHandleStatus,as same as account report
            return userService.handleUser(uid, monitorId, Status.UserHandleStatus.BLOK, blockPath, deletePath, blockTitle, deleteTitle);
        } else if (Const.Action.OUTF.equals(action)) {
            return userService.handleUser(uid, monitorId, Status.UserHandleStatus.OUTF, blockPath, deletePath, blockTitle, deleteTitle);
        } else if (Const.Action.MODIFYPROFILES.equals(action)) {
            return userService.updateUserProfiles(uid, monitorId, q, defaultName);
        } else if (Const.Action.UNOUT.equals(action) || Const.Action.UNOUTF.equals(action)) {
            return userService.handleUser(uid, monitorId, Status.UserStatus.ACTV, blockPath, deletePath, blockTitle, deleteTitle);
        } else if (Const.Action.MODIFYPASSWORD.equals(action)) {
            return userService.updateUserPassword(uid, monitorId, passwordEmaiTemplatePath, passwordEmailTitle);
        }
        throw new NotSupportActionException();
    }

    @Override
    public UserDTO findUser(long uid) throws Exception {
        User u = userService.seletctUser(uid);
        if (u == null) {
            throw new NotFoundUserException();
        }
        return new UserDTO(u);
    }

    @Override
    public Page<UserResult> findAllUser(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userService.seletctAllUsers(offset, limit, q);
        if (page == null) {
            throw new NotFoundUserException();
        }
        if (page.getContent().size() == 1) {
            page.getContent().get(0).setShareCount(findShareCount(page.getContent().get(0).getUid()));
            page.getContent().get(0).setAlertCount(findAlertCount(page.getContent().get(0).getUid()));
        }
        return page;
    }

    public Page<UserBlackResult> findAllBlackList(String q, Integer offset, Integer limit) throws Exception {
        return userService.selectUserBlackList(q, offset, limit);
    }

    @Override
    @Transactional(value = "commonTxManager")
    public void removeBlackUser(long blackId) throws Exception {
        userService.deleteBlackUser(blackId);
    }

    @Override
    @Transactional(value = "commonTxManager")
    public UserDTO editUserProfiles(Long uid, Long monitorId, String q, String defaultName) throws Exception {
        return userService.updateUserProfiles(uid, monitorId, q, defaultName);
    }

    @Override
    public UserInvalid findUserInvalidByUid(Long uid) throws Exception {
        return userService.selectUserInvalid(uid);
    }

    @Override
    @Transactional(value = "commonTxManager")
    public UserBlackList createBlackUser(Long uid, Long monitorId) throws Exception {
        return userService.insertBlackUser(uid, monitorId);
    }

    @Override
    public Page<UserResult> findAllUserSimple(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userService.seletctAllUsersSimple(offset, limit, q);
        if (page == null) {
            throw new NotFoundUserException();
        }
        return page;
    }

    @Override
    public Integer findTotalReportCount(Long uid) throws Exception {
        return userService.selectTotalReportCount(uid);
    }

    @Override
    public Integer findShareCount(Long uid) throws Exception {
        return userService.selectShareCount(uid);
    }

    @Override
    public Integer findAlertCount(Long uid) throws Exception {
        return userService.selectAlertCount(uid);
    }

    @Override
    @Transactional(value = "commonTxManager")
    public AlertResult editAlertCount(Long monitorId, Long uid, String alertType) throws Exception {
        return userService.updateAlertCount(monitorId, uid, alertType);
    }

    @Override
    public Page<UserResult> findUserList(String action, Integer offset, Integer limit, String q, String sort) throws Exception {
        Page<UserResult> pageResult = new PageImpl<UserResult>(new ArrayList<UserResult>(), new PageRequest(0, 1), 0);
        if (Const.Action.REPORTED.equals(action)) {
            pageResult = findAllReportedUser(Integer.valueOf(offset), Integer.valueOf(limit), q, sort);
        } else if (Const.Action.HANDLED.equals(action)) {
            pageResult = userService.seletctHandledUsers(Integer.valueOf(offset), Integer.valueOf(limit), q, sort);
        } else if (Const.Action.ALL.equals(action)) {
            pageResult = findAllUser(Integer.valueOf(offset), Integer.valueOf(limit), q);
        } else if (Const.Action.ALLSIMPLE.equals(action)) {
            pageResult = findAllUserSimple(Integer.valueOf(offset), Integer.valueOf(limit), q);
        }
        return pageResult;
    }

    @Override
    public ReportTypeCountResult findReportTypeCountResult(Long uid) throws Exception {
        return userReportService.selectReportTypeCountResult(uid);
    }

    @Override
    public List<UserHandleHistoryResult> findUserHandleHistory(Long uid) throws Exception {
        return userHandleHistoryService.selectUserHandleHistory(uid);
    }

    @Override
    public String findUserLoginType(Long uid) throws Exception {
        return userService.findUserLoginType(uid);
    }
}
