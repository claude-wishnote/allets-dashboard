package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.SlangWord;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jack on 2015/9/1.
 */
public interface ProhibitionRepository extends CrudRepository<SlangWord, Integer>,
        ProhibitionRepositoryCustom  {
}
