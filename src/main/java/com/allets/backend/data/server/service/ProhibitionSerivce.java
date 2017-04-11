package com.allets.backend.data.server.service;

import com.allets.backend.data.server.entity.common.SlangWord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jack on 2015/9/1.
 * The interface prohibition service.
 */
@Service
public interface ProhibitionSerivce {

    /**
     *select slang word list
     * @return
     * @throws Exception
     */
    List<SlangWord> selectSlangWordList(String field) throws Exception;

    /**
     * insert slang word
     * @param slangWord
     * @return
     * @throws Exception
     */
    SlangWord insertSlangWord(SlangWord slangWord) throws Exception;

    /**
     * update slang word
     * @param slangWord
     * @throws Exception
     */
    void updateSlangWord(SlangWord slangWord) throws Exception;

    /**
     * delete slang word
     * @param slangId
     * @throws Exception
     */
    void deleteSlangWord(int slangId) throws Exception;

    String selectSlangWordsText() throws Exception;

}
