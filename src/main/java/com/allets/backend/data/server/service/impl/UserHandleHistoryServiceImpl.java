package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.data.result.UserHandleHistoryResult;
import com.allets.backend.data.server.service.UserHandleHistoryService;
import com.allets.backend.data.server.repository.common.UserHandleHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by claude on 2016/1/31.
 */
@Service
public class UserHandleHistoryServiceImpl implements UserHandleHistoryService {

    @Autowired
    UserHandleHistoryRepository userHandleHistoryRepository;

    @Override
    public List<UserHandleHistoryResult> selectUserHandleHistory(Long uid) throws Exception {
        return userHandleHistoryRepository.findUserHandleHistoryByUid(uid);
    }
}
