package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.UserInvalid;
import org.springframework.data.repository.CrudRepository;

public interface UserInvalidRepository extends CrudRepository<UserInvalid, Integer>{

    UserInvalid findByUid(long uid);

    void deleteByUid(Long uid);
}
