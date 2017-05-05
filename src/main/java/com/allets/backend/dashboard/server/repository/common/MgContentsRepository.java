package com.allets.backend.dashboard.server.repository.common;

import com.allets.backend.dashboard.server.entity.common.MgContents;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by claude on 2016/1/24.
 */
public interface MgContentsRepository extends CrudRepository<MgContents, Integer>,MgContentsRepositoryCustom
{
}
