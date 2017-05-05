package com.allets.backend.dashboard.server.facade;

import com.allets.backend.dashboard.server.data.result.MgContentsResult;
import org.springframework.data.domain.PageImpl;

/**
 * Created by claude on 2016/1/24.
 */
public interface MgContentsFacade {

    PageImpl<MgContentsResult> findMgContents(String q, Integer offset, Integer limit)throws Exception;
}
