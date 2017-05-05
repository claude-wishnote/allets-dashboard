package com.allets.backend.dashboard.server.service.impl;

import com.allets.backend.dashboard.server.data.result.MgContentsResult;
import com.allets.backend.dashboard.server.repository.common.MgContentsRepository;
import com.allets.backend.dashboard.server.service.MgContentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

/**
 * Created by claude on 2016/1/24.
 */
@Service
public class MgContentsServiceImpl implements MgContentsService {

    final Logger log = LoggerFactory.getLogger(MgContentsServiceImpl.class);

    @Autowired
    MgContentsRepository mgContentsRepository;
    @Override
    public PageImpl<MgContentsResult> selectMgContents(String q, Integer offset, Integer limit) throws Exception {
        return mgContentsRepository.findMgContents(q,offset,limit);
    }
}
