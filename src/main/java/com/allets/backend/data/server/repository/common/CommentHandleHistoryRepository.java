package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.CommentHandleHistory;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by claude on 2015/8/29.
 */
public interface CommentHandleHistoryRepository extends CrudRepository<CommentHandleHistory, Integer>,
        CommentHandleHistoryRepositoryCustom{


}
