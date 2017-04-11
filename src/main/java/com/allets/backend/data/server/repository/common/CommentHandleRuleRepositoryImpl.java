package com.allets.backend.data.server.repository.common;

import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.allets.backend.data.server.entity.common.CommentHandleRule;
import com.allets.backend.data.server.entity.common.QCommentHandleRule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Created by claude on 2015/10/11.
 */
public class CommentHandleRuleRepositoryImpl implements CommentHandleRuleRepositoryCustom {

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    public void updateNCommentHandleRule(CommentHandleRule commentHandleRule) throws Exception {

        QCommentHandleRule qCommentHandleRule = QCommentHandleRule.commentHandleRule;
        JPAUpdateClause empUpdate = new JPAUpdateClause(entityManager, qCommentHandleRule);
        empUpdate
                .where(qCommentHandleRule.ruleId.eq(commentHandleRule.getRuleId()))
                .set(qCommentHandleRule.text, commentHandleRule.getLevel())
                .set(qCommentHandleRule.level, commentHandleRule.getLevel())
                .set(qCommentHandleRule.udate, new Date())
                .set(qCommentHandleRule.text, commentHandleRule.getText()).execute();
    }


}
