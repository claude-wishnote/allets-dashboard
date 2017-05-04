package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.*;
import org.springframework.data.domain.Page;

import java.util.List;


public interface UserFacade {

    Page<UserResult> findUserList(String action,Integer offset, Integer limit, String q,String sort) throws Exception;

    List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception;
}
