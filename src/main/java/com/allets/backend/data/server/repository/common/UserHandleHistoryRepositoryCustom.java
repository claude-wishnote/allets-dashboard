package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.UserHandleHistoryResult;

import java.util.List;

/**
 * Created by jack on 2015/9/6.
 */
public interface UserHandleHistoryRepositoryCustom {
    List<UserHandleHistoryResult> findUserHandleHistoryByUid(Long uid) throws Exception;
}
