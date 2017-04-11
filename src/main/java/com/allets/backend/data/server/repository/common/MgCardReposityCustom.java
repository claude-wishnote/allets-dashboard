package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.MgCardResult;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
public interface MgCardReposityCustom {
    List<MgCardResult> findMgCards(String q) throws Exception;
}
