package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.UserBlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<UserBlackList, Long>,
		BlackListRepositoryCustom {

	UserBlackList findByUid(long uid);

}
