package com.allets.backend.data.server.service;

import com.allets.backend.data.server.data.result.MgCardResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
@Service
public interface MgCardSetvice {
    List<MgCardResult> selectMgCards(String q) throws Exception;
}
