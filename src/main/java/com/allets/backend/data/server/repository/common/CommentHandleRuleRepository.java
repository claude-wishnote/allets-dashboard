package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.CommentHandleRule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jack on 2015/9/10.
 */

public interface CommentHandleRuleRepository extends CrudRepository<CommentHandleRule, Integer>,CommentHandleRuleRepositoryCustom {

    List<CommentHandleRule> findAllByParentRuleIdIsNull();

    List<CommentHandleRule> findAllByParentRuleIdOrderByLevelAsc(Integer parentRuleId);



}
