package com.allets.backend.data.server.service;

import com.allets.backend.data.server.entity.common.CommentHandleRule;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CommentHandleRuleSerivce {

    List<CommentHandleRule> selectAllByParentRuleIdIsNull() throws Exception;

    List<CommentHandleRule> selectAllByParentRuleIdOrderByLevelAsc(Integer parentRuleId) throws Exception;

    CommentHandleRule insertCommentHandleRule(Integer parentRuleId, String text, String level) throws  Exception;

    CommentHandleRule updateCommentHandleRule(Integer ruleId,Integer parentRuleId,String text,String level)throws  Exception;

    void deleteCommentHandleRule(Integer ruleId) throws  Exception;
}
