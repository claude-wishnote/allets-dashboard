package com.allets.backend.data.server.service.impl;


import com.allets.backend.data.server.consts.Status;
import com.allets.backend.data.server.data.dto.UserDTO;
import com.allets.backend.data.server.data.result.*;
import com.allets.backend.data.server.exception.NotFoundUserException;
import com.allets.backend.data.server.exception.NotSupportActionException;
import com.allets.backend.data.server.exception.NotSupportAlertTypeException;
import com.allets.backend.data.server.repository.common.UserRepository;
import com.allets.backend.data.server.utils.EmailSendJob;
import com.allets.backend.data.server.utils.EmailUtil;
import com.allets.backend.data.server.service.UserService;
import com.allets.backend.data.server.utils.EncryptUtil;
import com.allets.backend.data.server.utils.StringUtil;
import com.google.common.collect.ImmutableMap;
import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.entity.common.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
