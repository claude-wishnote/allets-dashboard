package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.*;
import com.allets.backend.data.server.entity.common.UserInvalid;
import com.allets.backend.data.server.entity.common.UserBlackList;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserFacade {

    /**
     * find reported user list
     * @param offset
     * @param limit
     * @param q
     * @return
     * @throws Exception
     */
    Page<UserResult> findAllReportedUser(Integer offset, Integer limit, String q , String sort) throws Exception;

    /**
     * Handle Reported User
     * pass, invalid for 3 month, delete
     * @param uid
     * @param status
     * @return
     * @throws Exception
     */
    UserDTO handleUser(String action, long uid, long monitorId, String q, String defaultName, String blockPath, String deletePath, String blockTitle, String deleteTitle, String passwordEmaiTemplatePath, String passwordEmailTitle) throws Exception;

    UserDTO findUser(long uid) throws Exception;

    Page<UserResult> findAllUser(Integer offset, Integer limit, String q) throws Exception;

    /**
     * get user black list
     *
     * @param q
     * @param offset
     * @param limit
     * @return
     * @throws Exception
     */
    Page<UserBlackResult> findAllBlackList(String q, Integer offset, Integer limit) throws Exception;

    /**
     * remove black user
     *
     * @param blackId
     */
    void removeBlackUser(long blackId) throws Exception;

    /**
     * this method will reset user peofiles.
     * user.name=""
     * user.introMessage=""
     * user.photo="A00/default_avatar.png"
     * subscribe=0
     * subscriber=0
     * @param uid
     * @param q
     * @return
     * @throws Exception
     */
    UserDTO editUserProfiles(Long uid,Long monitorId,String q,String defaultName) throws Exception;

    UserInvalid findUserInvalidByUid(Long uid) throws Exception;

    UserBlackList createBlackUser(Long uid,Long monitorId) throws Exception;

    Page<UserResult> findAllUserSimple(Integer offset, Integer limit, String q) throws Exception;

    Integer findTotalReportCount(Long uid) throws Exception;

    Integer findShareCount(Long uid) throws Exception;

    Integer findAlertCount(Long uid) throws Exception;

    AlertResult editAlertCount(Long monitorId, Long uid, String alertType) throws Exception;

    Page<UserResult> findUserList(String action,Integer offset, Integer limit, String q,String sort) throws Exception;

    ReportTypeCountResult findReportTypeCountResult(Long uid) throws Exception;

    List<UserHandleHistoryResult> findUserHandleHistory(Long uid) throws Exception;

    String findUserLoginType(Long uid) throws Exception;

}
