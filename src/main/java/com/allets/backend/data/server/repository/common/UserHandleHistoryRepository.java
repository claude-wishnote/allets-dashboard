package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.UserHandleHistory;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jack on 2015/9/6.
 */
public interface UserHandleHistoryRepository extends CrudRepository<UserHandleHistory, Integer>,
        UserHandleHistoryRepositoryCustom {

}
