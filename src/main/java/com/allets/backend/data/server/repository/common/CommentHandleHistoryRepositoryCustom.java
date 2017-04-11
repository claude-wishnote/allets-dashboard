package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.CommentResult;
import org.springframework.data.domain.Page;

/**
 * Created by claude on 2015/8/29.
 */
public interface CommentHandleHistoryRepositoryCustom {

    Page<CommentResult> findAllHandledComment(Integer offset, Integer limit, String q) throws Exception;

}
