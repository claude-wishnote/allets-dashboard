package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.data.result.MgContentsResult;
import com.allets.backend.data.server.repository.common.MgContentsRepository;
import com.allets.backend.data.server.service.MgContentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
