package com.allets.backend.data.server.repository.common;


import com.allets.backend.data.server.data.result.UserBlackResult;
import com.allets.backend.data.server.entity.common.UserBlackList;
import org.springframework.data.domain.Page;

public interface BlackListRepositoryCustom {

	Page<UserBlackResult> findAllBlackList(String q, Integer offset, Integer limit) throws Exception;

	UserBlackList createBlackList(UserBlackList userBlackList)  throws Exception;
}
