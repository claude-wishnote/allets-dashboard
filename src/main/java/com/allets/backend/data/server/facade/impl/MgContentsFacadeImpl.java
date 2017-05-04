package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.data.result.MgContentsResult;
import com.allets.backend.data.server.facade.MgContentsFacade;
import com.allets.backend.data.server.service.MgContentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
@Component
@Transactional(value = "commonTxManager", readOnly = true)
public class MgContentsFacadeImpl implements MgContentsFacade {

    @Autowired
    MgContentsService mgContentsService;
    @Override
    public PageImpl<MgContentsResult> findMgContents(String q, Integer offset, Integer limit) throws Exception {
        return mgContentsService.selectMgContents(q,offset,limit);
    }
}
