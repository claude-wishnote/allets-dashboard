package com.allets.backend.data.server.service;


import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.AlertResult;
import com.allets.backend.data.server.data.result.SpecialUser;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.entity.common.UserInvalid;
import com.allets.backend.data.server.data.result.UserBlackResult;
import com.allets.backend.data.server.entity.common.User;
import com.allets.backend.data.server.entity.common.UserBlackList;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO handleUser(long uid, long monitorId, String status, String blockPath, String deletePath, String blockTitle, String deleteTitle) throws Exception;

    User seletctUser(long uid) throws Exception;

    Page<UserResult> seletctAllUsers(Integer offset, Integer limit, String q) throws Exception;

    Page<UserBlackResult> selectUserBlackList(String q, Integer offset, Integer limit) throws Exception;

    void deleteBlackUser(long blackId) throws Exception ;

    UserDTO updateUserProfiles(Long uid,Long monitorId, String q,String defaultName) throws Exception;

    UserInvalid selectUserInvalid(Long uid)throws Exception;

    UserBlackList insertBlackUser(Long uid,Long monitorId) throws Exception;

    Page<UserResult> seletctAllUsersSimple(Integer offset, Integer limit, String q) throws Exception;

    Integer selectTotalReportCount(Long uid) throws Exception;

    Integer selectShareCount(Long uid) throws Exception;

    Integer selectAlertCount(Long uid) throws Exception;

    AlertResult updateAlertCount(Long monitorId, Long uid, String alertType) throws Exception;

    Page<UserResult> seletctHandledUsers(Integer offset, Integer limit, String q,String sort) throws Exception;

    List<SpecialUser> seletctSpecialUsers( ) throws Exception;

    String findUserLoginType(Long uid) throws Exception;

    UserDTO updateUserPassword(Long uid,Long monitorId,String eamilTemplatePath,String emailTitle) throws Exception;
}
