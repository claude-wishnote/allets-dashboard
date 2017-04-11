package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.MgComment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by claude on 2015/8/24.
 */
public interface CommentRepository extends CrudRepository<MgComment, Integer>,
        CommentRepositoryCustom{

        MgComment findByCommentId(long commentId);

}
