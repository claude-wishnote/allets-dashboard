package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.consts.Const;
import com.allets.backend.data.server.data.result.ReportTypeCountResult;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.repository.common.UserReportRepository;
import com.allets.backend.data.server.service.UserReportService;
import com.allets.backend.data.server.entity.common.UserReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReportServiceImpl implements UserReportService {

    final Logger log = LoggerFactory.getLogger(UserReportServiceImpl.class);
    @Autowired
    UserReportRepository userReportRepository;

    @Override
    public Page<UserResult> selectAllReportedUser(Integer offset, Integer limit, String searchBy, String keyword, String sort) throws Exception {
        String searchField = null;
        if (Const.SearchField.EMAIL.equals(searchBy) || Const.SearchField.NICKNAME.equals(searchBy) || Const.SearchField.KEYWORD.equals(searchBy)) {
            searchField = searchBy;
        }

        return userReportRepository.findAllReportedUser(offset, limit, searchBy, keyword,sort);
    }

    @Override
    public Page<UserReport> selectByPage(Integer page, Integer pageSize) throws Exception {
        return userReportRepository.findAll(new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "cdate")));
    }

    @Override
    public List<UserReport> selectByReportedUid(long uid) throws Exception {
        return userReportRepository.findByReportedUid(uid);
    }

    @Override
    public UserReport insertUserReport(UserReport userReport) throws Exception {
        return userReportRepository.save(userReport);
    }

    @Override
    public void deleteUserReport(int id) throws Exception {
        userReportRepository.delete(id);
    }

    @Override
    public ReportTypeCountResult selectReportTypeCountResult(Long uid) throws Exception {
        return userReportRepository.findReportTypeCountResult(uid);
    }
}
