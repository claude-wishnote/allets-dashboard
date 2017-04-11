package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import org.springframework.data.domain.Page;

public interface UserReportRepositoryCustom {

    Page<UserResult> findAllReportedUser(Integer offset, Integer limit, String searchBy, String keyword, String sort) throws Exception;

    ReportTypeCountResult findReportTypeCountResult(Long uid) throws Exception;
}
