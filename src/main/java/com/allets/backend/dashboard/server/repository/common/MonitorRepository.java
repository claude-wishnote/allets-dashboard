package com.allets.backend.dashboard.server.repository.common;

import com.allets.backend.dashboard.server.entity.common.Monitor;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MonitorRepository extends PagingAndSortingRepository<Monitor, Integer>,MonitorRepositoryCustum{

    Monitor findByNameAndPassword(String name, String password);

    Monitor findByName(String name);

    Monitor findByMonitorId(long monitorId);

    List<Monitor> findAll(Sort s);

}
