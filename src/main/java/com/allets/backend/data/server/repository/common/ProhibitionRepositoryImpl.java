package com.allets.backend.data.server.repository.common;

/**
 * Created by jack on 2015/9/1.
 */

import com.allets.backend.data.server.entity.common.SlangWord;
import com.allets.backend.data.server.entity.common.QSlangWord;
import com.google.common.base.Strings;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProhibitionRepositoryImpl implements ProhibitionRepositoryCustom {

    @PersistenceContext(unitName = "commonUnit")
    EntityManager entityManager;

    @Override
    public List<SlangWord> findAllSlangWord(String field) throws Exception {
        JPAQuery listQuery = new JPAQuery(entityManager);
        QSlangWord qSlangWord = QSlangWord.slangWord;
        listQuery.from(qSlangWord);
        if (!Strings.isNullOrEmpty(field))
            listQuery.where(qSlangWord.slang.like("%" + field + "%"));
        listQuery.orderBy(qSlangWord.slangId.desc());
        List<SlangWord> results = listQuery.list(qSlangWord);
        return results;
    }

    @Transactional(value = "commonTxManager")
    @Override
    public void updateSlangWord(SlangWord slangWord) throws Exception {
        QSlangWord qSlangWord = QSlangWord.slangWord;
        JPAUpdateClause slangUpdate = new JPAUpdateClause(entityManager, qSlangWord);
        slangUpdate.where(qSlangWord.slangId.eq(slangWord.getSlangId()))
                .set(qSlangWord.type, slangWord.getType())
                .set(qSlangWord.slang, slangWord.getSlang())
                .execute();
    }
}
