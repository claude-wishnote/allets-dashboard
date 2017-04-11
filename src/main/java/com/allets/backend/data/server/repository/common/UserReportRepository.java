package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.entity.common.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Integer>,
        UserReportRepositoryCustom {
    List<UserReport> findByReportedUid(long reportedUid);
}
