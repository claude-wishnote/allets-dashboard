package com.allets.backend.data.server.facade;

import com.allets.backend.data.server.data.result.MgCardResult;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
public interface MgCardFacade {

    List<MgCardResult> findMgCards(String q) throws Exception;

}
