package com.allets.backend.dashboard.server.service;


import com.allets.backend.dashboard.server.data.result.UserResult;
import com.allets.backend.dashboard.server.data.result.UserStatisticsResult;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    Page<UserResult> seletctAllUsers(Integer offset, Integer limit, String q) throws Exception;

    Page<UserResult> seletctAllUsersSimple(Integer offset, Integer limit, String q) throws Exception;

    List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception;

}
