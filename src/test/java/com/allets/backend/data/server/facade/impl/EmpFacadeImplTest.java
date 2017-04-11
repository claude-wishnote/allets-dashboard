package com.allets.backend.data.server.facade.impl;

import com.allets.backend.data.server.data.dto.EmpDeptDTO;
import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.facade.EmpFacade;
import com.allets.backend.data.server.utils.JsonUtil;
import com.allets.backend.data.server.config.RootApplicationContextConfig;
import com.allets.backend.data.server.entity.common.Emp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@TransactionConfiguration(transactionManager = "commonTxManager", defaultRollback = true)
@Transactional
public class EmpFacadeImplTest {


	final Logger log = LoggerFactory.getLogger(EmpFacadeImplTest.class);
	
	@Autowired
    EmpFacade facade;
	
	EmpDeptDTO empDeptDTO;
	Emp source;
	
	@Before
	public void before() throws Exception {
		
		this.source = new Emp();
		this.source.setEname("clay");
		this.source.setDeptno(10);
		
		this.empDeptDTO = new EmpDeptDTO();
		this.empDeptDTO.setEmp(this.source);
	}

	@Test
	public void selectEmpDeptList() throws Exception {

		Page<EmpDeptResult> results = facade.findEmpDeptAll();
		assertNotNull(results);
		log.info(JsonUtil.marshallingJson(results));
		
	}

}
