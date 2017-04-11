package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.CommentReport;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by claude on 2015/9/16.
 */
public interface  CommentReportRepository extends CrudRepository<CommentReport, Integer>,
        CommentReportRepositoryCunsom{
}
