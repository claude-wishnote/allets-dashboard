package com.allets.backend.dashboard.server.service.impl;


import com.allets.backend.dashboard.server.data.result.UserResult;
import com.allets.backend.dashboard.server.data.result.UserStatisticsResult;
import com.allets.backend.dashboard.server.utils.EmailUtil;
import com.allets.backend.dashboard.server.repository.common.UserRepository;
import com.allets.backend.dashboard.server.service.UserService;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    private VelocityEngine velocityEngine;

    private ExecutorService emailSendThread = Executors.newFixedThreadPool(3);




    @Override
    public Page<UserResult> seletctAllUsers(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userRepository.findUsers(offset, limit, q);
        return page;
    }

    @Override
    public Page<UserResult> seletctAllUsersSimple(Integer offset, Integer limit, String q) throws Exception {
        Page<UserResult> page = userRepository.findUsersSimple(offset, limit, q);
        return page;
    }

    @Override
    public List<UserStatisticsResult> findUserStatisticsResult(String q) throws Exception {
        return userRepository.findUserStatisticsResult(q);
    }
}
