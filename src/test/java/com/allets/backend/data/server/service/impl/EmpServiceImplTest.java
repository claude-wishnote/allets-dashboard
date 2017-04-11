package com.allets.backend.data.server.service.impl;

import com.allets.backend.data.server.config.RootApplicationContextConfig;
import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.entity.common.Emp;
import com.allets.backend.data.server.utils.JsonUtil;
import com.allets.backend.data.server.service.EmpService;
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
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@TransactionConfiguration(transactionManager = "commonTxManager", defaultRollback = true)
@Transactional
public class EmpServiceImplTest {

	final Logger log = LoggerFactory.getLogger(EmpServiceImplTest.class);

	@Autowired
    EmpService service;

	Emp source;

	@Before
	public void before() throws Exception {
		this.source = new Emp();
		this.source.setEname("clay" + System.currentTimeMillis());
		this.source.setDeptno(5);
	}

	@Test
	public void selectByDeptnoList() throws Exception {
		Emp result = service.insertEmp(this.source);
		Page<EmpDeptResult> list = service.selectByDeptnoList();
		assertNotNull(list);
		log.info(JsonUtil.marshallingJsonWithPretty(list));
	}

	@Test
	public void insertEmp() throws Exception {
		Emp result = service.insertEmp(this.source);
		assertNotNull(result);
		log.info(JsonUtil.marshallingJsonWithPretty(result));
	}

	@Test
	public void updateEmp() throws Exception {
		Emp result = service.insertEmp(this.source);
		result.setEname("clay-update" + System.currentTimeMillis());
		service.updateEmp(result);
	}

	@Test
	public void deleteEmp() throws Exception {
		Emp result = service.insertEmp(this.source);
		service.deleteEmp(result.getEmpno());

		result = service.selectByEmpno(result.getEmpno());
		assertNull(result);

	}
	
}
