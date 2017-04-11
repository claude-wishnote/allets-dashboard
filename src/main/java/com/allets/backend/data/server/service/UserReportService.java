package com.allets.backend.data.server.service;

import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.entity.common.UserReport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserReportService {

    /**
     * select reported user list.
     *
     * @param offset
     * @param limit
     * @param searchBy
     * @param keyword
     * @return
     * @throws Exception
     */
    Page<UserResult> selectAllReportedUser(Integer offset, Integer limit, String searchBy, String keyword, String sort) throws Exception;


    /**
     * select user report history
     *
     * @return the list
     * @throws Exception the exception
     */
    Page<UserReport> selectByPage(Integer page, Integer pageSize) throws Exception;


    /**
     * select user's reported history
     *
     * @param uid
     * @return UserReport
     * @throws Exception
     */
    List<UserReport> selectByReportedUid(long uid) throws Exception;

    /**
     * insert user report
     *
     * @param userReport
     * @return
     * @throws Exception
     */
    UserReport insertUserReport(UserReport userReport) throws Exception;


    /**
     * delete user report
     *
     * @param id
     * @throws Exception
     */
    void deleteUserReport(int id) throws Exception;

    ReportTypeCountResult selectReportTypeCountResult(Long uid) throws Exception;

}
