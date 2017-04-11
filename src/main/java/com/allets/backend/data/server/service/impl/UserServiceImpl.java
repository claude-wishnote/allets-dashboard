package com.allets.backend.data.server.service.impl;


import com.allets.backend.data.server.consts.Status;
import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.AlertResult;
import com.allets.backend.data.server.data.result.SpecialUser;
import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.exception.NotSupportActionException;
import com.allets.backend.data.server.exception.NotSupportAlertTypeException;
import com.allets.backend.data.server.repository.common.UserRepository;
import com.allets.backend.data.server.utils.EmailSendJob;
import com.allets.backend.data.server.utils.EmailUtil;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.entity.common.UserHandleHistory;
import com.allets.backend.data.server.repository.common.BlackListRepository;
import com.allets.backend.data.server.service.UserService;
import com.allets.backend.data.server.utils.EncryptUtil;
import com.allets.backend.data.server.utils.StringUtil;
import com.google.common.collect.ImmutableMap;
import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.data.result.UserBlackResult;
import com.allets.backend.data.server.entity.common.User;
import com.allets.backend.data.server.entity.common.UserBlackList;
import com.allets.backend.data.server.entity.common.UserInvalid;
import com.allets.backend.data.server.repository.common.UserHandleHistoryRepository;
import com.allets.backend.data.server.repository.common.UserInvalidRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInvalidRepository userInvalidRepository;

    @Autowired
    UserHandleHistoryRepository userHandleHistoryRepository;

    @Autowired
    BlackListRepository blackListRepository;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    private VelocityEngine velocityEngine;

    private ExecutorService emailSendThread = Executors.newFixedThreadPool(3);

    @Override
    public UserDTO handleUser(long uid, long monitorId, String status, String blockPath, String deletePath, String blockTitle, String deleteTitle) throws Exception {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            throw new NotFoundUserException();
        }
        UserDTO userDTO = null;
        // update invalid
        if (Status.UserHandleStatus.BLOK.equals(status)) {
            //1,insert/update user invalid (invalid 3 month)
            UserInvalid userInvalid = userInvalidRepository.findByUid(uid);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 3);
            Date invalidTo = cal.getTime();
            if (userInvalid == null) {
                userInvalid = new UserInvalid();
            }
            userInvalid.setUid(uid);
            userInvalid.setInvaildFrom(new Date());
            userInvalid.setInvaildTo(invalidTo);
            if (!Status.UserStatus.OUT.equals(user.getStatus()) && !Status.UserStatus.DEL.equals(user.getStatus())) {
                //just need user's alive previous status
                userInvalid.setPreviousStatus(user.getStatus());
            }
            userInvalidRepository.save(userInvalid);

            //2,save user.
            user.setStatus(Status.UserStatus.OUT);
            userRepository.saveAndFlush(user);

            //3,insert user handle history
            UserHandleHistory userHandleHistory = new UserHandleHistory();
            userHandleHistory.setUid(uid);
            userHandleHistory.setHandleResult(Status.UserHistoryHandleStatus.OUT);
            userHandleHistory.setMonitorId(monitorId);
            userHandleHistoryRepository.save(userHandleHistory);

            //4,update user report handle result
            userRepository.updateUserReportHandleResults(uid, status);

            userDTO = new UserDTO(user);
            userDTO.setInvalidFrom(userInvalid.getInvaildFrom());
            userDTO.setInvalidTo(userInvalid.getInvaildTo());
            userDTO.setPreviousStatus(userInvalid.getPreviousStatus());
        } else if (Status.UserHandleStatus.OUTF.equals(status)) {
            //1,insert/update user invalid (invalid forever)
            UserInvalid userInvalid = userInvalidRepository.findByUid(uid);
            if (userInvalid == null) {
                userInvalid = new UserInvalid();
            }
            userInvalid.setUid(uid);
            userInvalid.setInvaildFrom(new Date());
            userInvalid.setInvaildTo(null);
            if (!Status.UserStatus.OUT.equals(user.getStatus()) && !Status.UserStatus.DEL.equals(user.getStatus())) {
                //just need user's alive previous status
                userInvalid.setPreviousStatus(user.getStatus());
            }
            userInvalidRepository.save(userInvalid);

            //2,save user.
            user.setStatus(Status.UserStatus.OUT);
            userRepository.saveAndFlush(user);

            //3,insert user handle history
            UserHandleHistory userHandleHistory = new UserHandleHistory();
            userHandleHistory.setUid(uid);
            userHandleHistory.setHandleResult(Status.UserHistoryHandleStatus.OUTF);
            userHandleHistory.setMonitorId(monitorId);
            userHandleHistoryRepository.save(userHandleHistory);

            //4,update user report handle result
            userRepository.updateUserReportHandleResults(uid, Status.UserStatus.DEL);

            userDTO = new UserDTO(user);
            userDTO.setInvalidFrom(userInvalid.getInvaildFrom());
            userDTO.setInvalidTo(userInvalid.getInvaildTo());
            userDTO.setPreviousStatus(userInvalid.getPreviousStatus());
        } else if (Status.UserStatus.ACTV.equals(status)) {
            UserInvalid userInvalid = userInvalidRepository.findByUid(uid);
            if (userInvalid != null && userInvalid.getPreviousStatus() != null) {
                user.setStatus(userInvalid.getPreviousStatus());
            } else {
                user.setStatus(status);
            }
            //1,insert user handle history
            UserHandleHistory userHandleHistory = new UserHandleHistory();
            userHandleHistory.setUid(uid);
            userHandleHistory.setHandleResult(Status.UserHistoryHandleStatus.REC);
            userHandleHistory.setMonitorId(monitorId);
            userHandleHistoryRepository.save(userHandleHistory);
            //2,save user.
            userRepository.saveAndFlush(user);
            //3,delete .user invalid
            userInvalidRepository.deleteByUid(uid);

            userDTO = new UserDTO(user);
        } else if (Status.UserHandleStatus.PASS.equals(status)) {
            //PASS only handle report and insert history
            //do not send email
            UserHandleHistory userHandleHistory = new UserHandleHistory();
            userHandleHistory.setUid(uid);
            userHandleHistory.setHandleResult(Status.UserHistoryHandleStatus.PASS);
            userHandleHistory.setMonitorId(monitorId);
            userHandleHistoryRepository.save(userHandleHistory);

            userRepository.updateUserReportHandleResults(uid, status);

            userDTO = new UserDTO(user);
            return userDTO;
        }
        if (userDTO == null) {
            throw new NotSupportActionException();
        }
        sendHandleEmail(status, user, blockPath, deletePath, blockTitle, deleteTitle);
        return userDTO;
    }

    private void sendResetPasswordEmail(User user, String newPasswordForEmail, String eamilTemplatePath, String emailTitle) throws Exception {
        Map<String, Object> m = ImmutableMap.<String, Object>builder()
                .put("userName", user.getName())
                .put("newPasswordForEmail", newPasswordForEmail)
                .build();
        String contentHtml = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, eamilTemplatePath, m);
        System.err.println(emailTitle);
        System.err.println(contentHtml);
        emailSendThread.submit(new EmailSendJob(emailUtil, null, user.getEmail(), emailTitle, contentHtml));
    }

    private void sendHandleEmail(String status, User user, String blockPath, String deletePath, String blockTitle, String deleteTitle) throws Exception {
        if (StringUtils.isNotBlank(user.getEmail())) {
            if (Status.UserHandleStatus.OUT.equals(status)) {
                UserInvalid userInvalid = selectUserInvalid(Long.valueOf(user.getUid()));
                Calendar calendarFrom = Calendar.getInstance();
                calendarFrom.setTime(userInvalid.getInvaildFrom());
                Calendar calendarTo = Calendar.getInstance();
                calendarTo.setTime(userInvalid.getInvaildTo());
                Map<String, Object> m = ImmutableMap.<String, Object>builder()
                        .put("invalidFromYear", calendarFrom.get(Calendar.YEAR))
                        .put("invalidFromMonth", calendarFrom.get(Calendar.MONTH) + 1)
                        .put("invalidFromDay", calendarFrom.get(Calendar.DAY_OF_MONTH))
                        .put("invalidToYear", calendarTo.get(Calendar.YEAR))
                        .put("invalidToMonth", calendarTo.get(Calendar.MONTH) + 1)
                        .put("invalidToDay", calendarTo.get(Calendar.DAY_OF_MONTH))
                        .put("userEmail", user.getEmail())
                        .put("userNickEmail", user.getName())
                        .build();
//          String html = FileUtils.readFileToString(new File(new File(UserServiceImpl.class.getResource("/").toURI()).getParentFile().getParentFile().getAbsolutePath() + "/WEB-INF/pages/email/email_account_forbid_three_month.html"));
                String contentHtml = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, blockPath, m);
                emailSendThread.submit(new EmailSendJob(emailUtil, null, user.getEmail(), blockTitle, contentHtml));
            } else if (Status.UserHandleStatus.OUTF.equals(status)) {
                UserInvalid userInvalid = selectUserInvalid(Long.valueOf(user.getUid()));
                if (userInvalid == null) {
                    userInvalid.setInvaildFrom(new Date());
                }
                Calendar calendarFrom = Calendar.getInstance();
                calendarFrom.setTime(userInvalid.getInvaildFrom());
                Map<String, Object> m = ImmutableMap.<String, Object>builder()
                        .put("invalidFromYear", calendarFrom.get(Calendar.YEAR))
                        .put("invalidFromMonth", calendarFrom.get(Calendar.MONTH) + 1)
                        .put("invalidFromDay", calendarFrom.get(Calendar.DAY_OF_MONTH))
                        .put("userEmail", user.getEmail())
                        .put("userNickEmail", user.getName())
                        .build();
                String contentHtml = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, deletePath, m);
                emailSendThread.submit(new EmailSendJob(emailUtil, null, user.getEmail(), deleteTitle, contentHtml));
            }
        }
    }

    @Override
    public Page<UserBlackResult> selectUserBlackList(String q, Integer offset, Integer limit) throws Exception {
        return blackListRepository.findAllBlackList(q, offset, limit);
    }

    @Override
    public void deleteBlackUser(long blackId) throws Exception {
        blackListRepository.delete(blackId);
    }

    @Override
    public User seletctUser(long uid) throws Exception {
        User user = userRepository.findByUid(uid);
        return user;
    }

    @Override
    public Page<UserResult> seletctAllUsers(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userRepository.findUsers(offset, limit, q);
        return page;
    }

    @Override
    public UserDTO updateUserProfiles(Long uid, Long monitorId, String q, String defaultName) throws Exception {
        UserDTO userDTO = null;
        Page<UserResult> userResults = null;
        UserResult userResult = null;

        userResults = seletctAllUsers(0, 1, "uid=" + uid);
        if (userResults != null && userResults.getContent() != null && userResults.getContent().size() > 0) {
            userResult = userResults.getContent().get(0);
        }
        if (userResult != null) {

            userDTO = userRepository.updateUserProfiles(userResult, monitorId, q, defaultName);
            UserHandleHistory userHandleHistory = new UserHandleHistory();
            userHandleHistory.setUid(uid);
            userHandleHistory.setHandleResult(Const.Action.EDIT);
            userHandleHistory.setMonitorId(monitorId);
            userHandleHistoryRepository.save(userHandleHistory);
            return userDTO;
        } else {
            throw new NotFoundUserException();
        }
    }


    @Override
    public UserInvalid selectUserInvalid(Long uid) throws Exception {
        return userInvalidRepository.findByUid(uid);
    }

    @Override
    public UserBlackList insertBlackUser(Long uid, Long monitorId) throws Exception {
        User user = userRepository.findByUid(uid);
        UserBlackList userBlackList = blackListRepository.findByUid(uid);
        if (userBlackList == null) {
            if (user != null) {
                System.out.println("userBlackList");
                userBlackList = new UserBlackList();
                userBlackList.setMonitorId(monitorId);
                userBlackList.setUid(uid);
                userBlackList.setCdate(new Date());
                blackListRepository.save(userBlackList);
            } else {
                throw new NotFoundUserException();
            }
        }
        return userBlackList;
    }

    @Override
    public Page<UserResult> seletctAllUsersSimple(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userRepository.findUsersSimple(offset, limit, q);
        return page;
    }

    @Override
    public Integer selectTotalReportCount(Long uid) throws Exception {
        return userRepository.findTotalReportCount(uid);
    }

    @Override
    public Integer selectShareCount(Long uid) throws Exception {
        return userRepository.findShareCount(uid);
    }

    @Override
    public Integer selectAlertCount(Long uid) throws Exception {
        return userRepository.findAlertCount(uid);
    }

    @Override
    public AlertResult updateAlertCount(Long monitorId, Long uid, String alertType) throws Exception {
        if (alertType.equals("R")) {
            if (selectAlertCount(uid) == 0) {
                AlertResult alertResult = null;
                alertResult = userRepository.updateAlertCount(uid, Const.AlertType.A101.toString());
                UserHandleHistory userHandleHistory = new UserHandleHistory();
                userHandleHistory.setUid(uid);
                userHandleHistory.setHandleResult(Const.Action.ALT1);
                userHandleHistory.setMonitorId(monitorId);
                userHandleHistoryRepository.save(userHandleHistory);
                return alertResult;
            } else {
                AlertResult alertResult = null;
                alertResult = userRepository.updateAlertCount(uid, Const.AlertType.A102.toString());
                UserHandleHistory userHandleHistory = new UserHandleHistory();
                userHandleHistory.setUid(uid);
                userHandleHistory.setHandleResult(Const.Action.ALT1);
                userHandleHistory.setMonitorId(monitorId);
                userHandleHistoryRepository.save(userHandleHistory);
                return alertResult;
            }
        } else if (alertType.equals("D")) {
            AlertResult alertResult = null;
            alertResult = userRepository.updateAlertCount(uid, Const.AlertType.A200.toString());
            UserHandleHistory userHandleHistory = new UserHandleHistory();
            userHandleHistory.setUid(uid);
            userHandleHistory.setHandleResult(Const.Action.ALT2);
            userHandleHistory.setMonitorId(monitorId);
            userHandleHistoryRepository.save(userHandleHistory);
            return alertResult;
        } else {
            throw new NotSupportAlertTypeException();
        }
    }

    @Override
    public Page<UserResult> seletctHandledUsers(Integer offset, Integer limit, String q, String sort) throws Exception {
        return userRepository.findHandledUsers(offset, limit, q, sort);
    }

    @Override
    public List<SpecialUser> seletctSpecialUsers() throws Exception {
        return userRepository.findSpecialUsers();
    }

    @Override
    public String findUserLoginType(Long uid) throws Exception {
        return userRepository.selectUserLoginType(uid);
    }

    @Override
    public UserDTO updateUserPassword(Long uid, Long monitorId, String eamilTemplatePath, String emailTitle) throws Exception {
        User user = userRepository.findByUid(uid);
        if (user == null) {
            throw new NotFoundUserException();
        }
        String newPasswordForEmail = StringUtil.randomString(6);
        String newPasswordForDB = EncryptUtil.encryptMd5String(newPasswordForEmail);
        userRepository.updateUserPassword(uid, newPasswordForDB);

        UserHandleHistory userHandleHistory = new UserHandleHistory();
        userHandleHistory.setUid(uid);
        userHandleHistory.setHandleResult(Const.Action.RSPW);
        userHandleHistory.setMonitorId(monitorId);
        userHandleHistoryRepository.save(userHandleHistory);

        sendResetPasswordEmail(user, newPasswordForEmail, eamilTemplatePath, emailTitle);
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(uid);
        return userDTO;
    }
}
