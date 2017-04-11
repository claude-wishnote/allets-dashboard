package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.data.result.MgCardResult;
import com.allets.backend.data.server.facade.MgCardFacade;
import com.allets.backend.data.server.service.MgCardSetvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class MgCardFacadeImpl implements MgCardFacade {

    @Autowired
    MgCardSetvice mgCardSetvice;

    @Override
    public List<MgCardResult> findMgCards(String q) throws Exception {
        return mgCardSetvice.selectMgCards(q);
    }
}
