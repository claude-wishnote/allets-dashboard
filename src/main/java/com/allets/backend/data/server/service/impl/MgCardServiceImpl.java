package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.data.result.MgCardResult;
import com.allets.backend.data.server.service.MgCardSetvice;
import com.allets.backend.data.server.repository.common.MgCardReposity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
@Service
public class MgCardServiceImpl implements MgCardSetvice {

    final Logger log = LoggerFactory.getLogger(MgCardServiceImpl.class);
    @Autowired
    MgCardReposity mgCardReposity;

    @Override
    public List<MgCardResult> selectMgCards(String q) throws Exception {
        return mgCardReposity.findMgCards(q);
    }
}
