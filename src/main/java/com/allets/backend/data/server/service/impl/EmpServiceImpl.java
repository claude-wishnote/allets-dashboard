package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.repository.common.EmpRepository;
import com.allets.backend.data.server.service.EmpService;
import com.allets.backend.data.server.entity.common.Emp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * example service
 */
@Service
public class EmpServiceImpl implements EmpService {

	final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);
	
	@Autowired
    EmpRepository empRepository;

	@Override
	public Page<EmpDeptResult> selectByDeptnoList() throws Exception {
		return empRepository.findListByDeptno();
	}

	@Override
	public Emp selectByEmpno(int empno) throws Exception {
		return empRepository.findOne(empno);
	}

	@Override
	public Emp insertEmp(Emp emp) throws Exception {
		return empRepository.save(emp);
	}

	@Override
	public void updateEmp(Emp emp) throws Exception {
		empRepository.updateName(emp);
	}

	@Override
	public void deleteEmp(int empno) throws Exception {
		empRepository.delete(empno);
		
	}
}
