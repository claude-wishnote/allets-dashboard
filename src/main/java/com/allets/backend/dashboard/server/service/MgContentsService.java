package com.allets.backend.dashboard.server.service;

import com.allets.backend.dashboard.server.data.result.MgContentsResult;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

/**
 * Created by claude on 2016/1/24.
 */
@Service
public interface MgContentsService {
    PageImpl<MgContentsResult> selectMgContents(String q, Integer offset, Integer limit) throws Exception;
}
