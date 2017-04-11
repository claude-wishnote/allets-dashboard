package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.MgCard;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by claude on 2016/1/24.
 */
public interface MgCardReposity extends CrudRepository<MgCard, Long>, MgCardReposityCustom {
}
