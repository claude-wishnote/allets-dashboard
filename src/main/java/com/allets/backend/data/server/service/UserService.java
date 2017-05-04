package com.allets.backend.data.server.service;


import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.*;
import com.allets.backend.data.server.entity.common.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    Page<UserResult> seletctAllUsers(Integer offset, Integer limit, String q) throws Exception;

    Page<UserResult> seletctAllUsersSimple(Integer offset, Integer limit, String q) throws Exception;

    List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception;

}
