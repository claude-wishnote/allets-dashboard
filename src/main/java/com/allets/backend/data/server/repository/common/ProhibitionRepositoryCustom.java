package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.SlangWord;

import java.util.List;

/**
 * Created by jack on 2015/9/1.
 */
public interface ProhibitionRepositoryCustom {

    List<SlangWord> findAllSlangWord(String field) throws Exception;

    void updateSlangWord(SlangWord slangWord) throws Exception;

}
