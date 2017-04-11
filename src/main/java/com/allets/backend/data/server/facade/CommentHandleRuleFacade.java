package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.entity.common.CommentHandleRule;

import java.util.List;

/**
 * Created by claude on 2015/10/10.
 */
public interface CommentHandleRuleFacade {

    List<CommentHandleRule> findAllByParentRuleIdIsNull() throws Exception;

    List<CommentHandleRule> findAllByParentRuleIdOrderByLevelAsc(Integer parentRuleId) throws Exception;

    CommentHandleRule createCommentHandleRule(Integer parentRuleId,String text,String level) throws  Exception;

    CommentHandleRule modifyCommentHandleRule(Integer ruleId,Integer parentRuleId,String text,String level) throws  Exception;

    void removeCommentHandleRule(Integer ruleId) throws  Exception;
}
