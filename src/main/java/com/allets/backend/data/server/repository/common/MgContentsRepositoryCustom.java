package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.MgContentsResult;
import org.springframework.data.domain.PageImpl;

import java.util.List;

/**
 * Created by claude on 2016/1/24.
 */
public interface MgContentsRepositoryCustom {

    PageImpl<MgContentsResult> findMgContents(String q, Integer offset, Integer limit) throws Exception;
}
