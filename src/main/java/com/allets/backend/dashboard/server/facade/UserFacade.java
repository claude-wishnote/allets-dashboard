package com.allets.backend.dashboard.server.facade;

import com.allets.backend.dashboard.server.data.result.UserResult;
import com.allets.backend.dashboard.server.data.result.UserStatisticsResult;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserFacade {

    Page<UserResult> findUserList(String action, Integer offset, Integer limit, String q, String sort) throws Exception;

    List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception;
}
