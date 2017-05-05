package com.allets.backend.dashboard.server.repository.common;

import com.allets.backend.dashboard.server.data.result.UserResult;
import com.allets.backend.dashboard.server.data.result.UserStatisticsResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRepositoryCustom {


    public Page<UserResult> findUsers(Integer offset, Integer limit, String q) throws Exception;


    public Page<UserResult> findUsersSimple(Integer offset, Integer limit, String q) throws Exception;

    List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception;
}
