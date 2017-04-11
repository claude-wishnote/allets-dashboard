package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.AlertResult;
import com.allets.backend.data.server.data.result.SpecialUser;
import com.allets.backend.data.server.data.result.UserResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRepositoryCustom {

    public void updateUserReportHandleResults(Long uid,  String action) throws Exception;

    public Page<UserResult> findUsers(Integer offset, Integer limit, String q) throws Exception;

    public UserDTO updateUserProfiles(UserResult userResult, Long monitorId, String q, String defaultName) throws Exception;

    public Page<UserResult> findUsersSimple(Integer offset, Integer limit, String q) throws Exception;

    Integer findTotalReportCount(Long uid) throws Exception;

    Integer findShareCount(Long uid) throws Exception;

    Integer findAlertCount(Long uid) throws Exception;

    AlertResult updateAlertCount (Long uid, String alertType) throws Exception;

    Page<UserResult> findHandledUsers(Integer offset, Integer limit, String q,String sort) throws Exception;

    List<SpecialUser> findSpecialUsers() throws Exception;

    void deleteAlertQueue(Long uid,String alertType) throws Exception;

    String selectUserLoginType(Long uid) throws Exception;

    Integer updateUserPassword(Long uid,String password) throws Exception;
}
