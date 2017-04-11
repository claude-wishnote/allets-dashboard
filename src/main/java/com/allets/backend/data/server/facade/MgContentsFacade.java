package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.data.result.MgContentsResult;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
public interface MgContentsFacade {

    List<MgContentsResult> findMgContents(String q, Integer offset, Integer limit)throws Exception;
}
