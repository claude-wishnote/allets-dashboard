package com.allets.backend.data.server.service;

import com.allets.backend.data.server.data.result.UserHandleHistoryResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by claude on 2016/1/31.
 */
@Service
public interface UserHandleHistoryService {
    public List<UserHandleHistoryResult> selectUserHandleHistory(Long uid) throws Exception;

}
