package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.facade.CommentHandleRuleFacade;
import com.allets.backend.data.server.entity.common.CommentHandleRule;
import com.allets.backend.data.server.service.CommentHandleRuleSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by claude on 2015/10/10.
 */
@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class CommentHandleRuleFacadeImpl implements CommentHandleRuleFacade {

    @Autowired
    CommentHandleRuleSerivce commentHandleRuleSerivce;


    @Override
    public List<CommentHandleRule> findAllByParentRuleIdIsNull() throws Exception {
        return commentHandleRuleSerivce.selectAllByParentRuleIdIsNull();
    }

    @Override
    public List<CommentHandleRule> findAllByParentRuleIdOrderByLevelAsc(Integer parentRuleId) throws Exception {
        return commentHandleRuleSerivce.selectAllByParentRuleIdOrderByLevelAsc(parentRuleId);
    }

    @Transactional(value = "commonTxManager")
    @Override
    public CommentHandleRule createCommentHandleRule(Integer parentRuleId, String text, String level) throws Exception {
        return commentHandleRuleSerivce.insertCommentHandleRule(parentRuleId,text,level);
    }

    @Transactional(value = "commonTxManager")
    @Override
    public CommentHandleRule modifyCommentHandleRule(Integer ruleId, Integer parentRuleId, String text, String level) throws Exception {
        return commentHandleRuleSerivce.updateCommentHandleRule(ruleId,parentRuleId,text,level);
    }

    @Transactional(value = "commonTxManager")
    @Override
    public void removeCommentHandleRule(Integer ruleId) throws Exception {
         commentHandleRuleSerivce.deleteCommentHandleRule(ruleId);
    }
}
