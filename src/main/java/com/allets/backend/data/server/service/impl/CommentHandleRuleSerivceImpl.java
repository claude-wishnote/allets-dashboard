package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.exception.BadRequestException;
import com.allets.backend.data.server.exception.NotFountParentRuleException;
import com.allets.backend.data.server.exception.TextIsTooLongException;
import com.allets.backend.data.server.repository.common.CommentHandleRuleRepository;
import com.allets.backend.data.server.entity.common.CommentHandleRule;
import com.allets.backend.data.server.service.CommentHandleRuleSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by claude on 2015/10/10.
 */
@Service
public class CommentHandleRuleSerivceImpl implements CommentHandleRuleSerivce {

    final Logger log = LoggerFactory.getLogger(CommentHandleRuleSerivceImpl.class);
    @Autowired
    CommentHandleRuleRepository commentHandleRuleRepository;


    @Override
    public List<CommentHandleRule> selectAllByParentRuleIdIsNull() throws Exception {
        return commentHandleRuleRepository.findAllByParentRuleIdIsNull();
    }

    @Override
    public List<CommentHandleRule> selectAllByParentRuleIdOrderByLevelAsc(Integer parentRuleId) throws Exception {
        return commentHandleRuleRepository.findAllByParentRuleIdOrderByLevelAsc(parentRuleId);
    }

    @Override
    public CommentHandleRule insertCommentHandleRule(Integer parentRuleId, String text, String level) throws Exception {
        if (text.length() > 100) {
            throw new TextIsTooLongException();
        }
        if (commentHandleRuleRepository.findOne(parentRuleId) != null) {
            CommentHandleRule commentHandleRule = new CommentHandleRule();
            commentHandleRule.setParentRuleId(parentRuleId);
            commentHandleRule.setText(text);
            commentHandleRule.setLevel(level);
            commentHandleRule.setCdate(new Date());
            commentHandleRule.setUdate(new Date());
            return commentHandleRuleRepository.save(commentHandleRule);
        } else {
            throw new NotFountParentRuleException();
        }
    }

    @Override
    public CommentHandleRule updateCommentHandleRule(Integer ruleId, Integer parentRuleId, String text, String level) throws Exception {
        if (text.length() > 100) {
            throw new TextIsTooLongException();
        }
        CommentHandleRule commentHandleRule = new CommentHandleRule();
        if (commentHandleRuleRepository.findOne(ruleId) != null) {
            commentHandleRule.setRuleId(ruleId);
            commentHandleRule.setParentRuleId(parentRuleId);
            commentHandleRule.setText(text);
            commentHandleRule.setLevel(level);
            commentHandleRuleRepository.updateNCommentHandleRule(commentHandleRule);
        } else {
            throw new BadRequestException();
        }
        return commentHandleRule;
    }

    @Override
    public void deleteCommentHandleRule(Integer ruleId) throws Exception {
        commentHandleRuleRepository.delete(ruleId);
    }
}
