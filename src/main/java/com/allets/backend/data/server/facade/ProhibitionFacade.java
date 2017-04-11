package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.entity.common.SlangWord;

import java.util.List;

/**
 * Created by jack on 2015/9/1.
 */
public interface ProhibitionFacade {

    /**
     *
     * @param field
     * @return
     * @throws Exception
     */
    List<SlangWord> findSlangWordAll(String field) throws Exception;

    /**
     *
     * @param slangWord
     * @throws Exception
     */
    SlangWord createSlangWord(SlangWord slangWord) throws Exception;


    /**
     *
     * @param slangWord
     * @throws Exception
     */
    void modifySlangWord(SlangWord slangWord) throws Exception;

    /**
     * remove slang word
     * @param slangId
     * @throws Exception the exception
     */
    void removeSlangWord(Integer slangId) throws Exception;
}
