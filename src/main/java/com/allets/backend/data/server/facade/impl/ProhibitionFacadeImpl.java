package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.entity.common.SlangWord;
import com.allets.backend.data.server.facade.ProhibitionFacade;
import com.allets.backend.data.server.service.ProhibitionSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jack on 2015/9/1.
 * slang word
 */
@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class ProhibitionFacadeImpl implements ProhibitionFacade {

    @Autowired
    ProhibitionSerivce prohibitionSerivce;
    /**
     *
     * @return
     * @throws Exception the exception
     */
    @Override
    public List<SlangWord> findSlangWordAll(String field) throws Exception {
        return prohibitionSerivce.selectSlangWordList(field);
    }

    /**
     *
     * @param slangWord
     * @throws Exception
     */
    @Transactional(value = "commonTxManager")
    @Override
    public SlangWord createSlangWord(SlangWord slangWord) throws Exception {
        return prohibitionSerivce.insertSlangWord(slangWord);
    }


    /**
     *
     * @param slangWord
     * @throws Exception
     */
    @Transactional(value = "commonTxManager")
    @Override
    public void modifySlangWord(SlangWord slangWord) throws Exception {
        prohibitionSerivce.updateSlangWord(slangWord);
    }

    /**
     *
     * @param slangId
     * @throws Exception the exception
     */
    @Transactional(value = "commonTxManager")
    @Override
    public void removeSlangWord(Integer slangId) throws Exception {
        prohibitionSerivce.deleteSlangWord(slangId);
    }
}
