package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.config.RootApplicationContextConfig;
import com.allets.backend.data.server.data.result.EmpDeptResult;
import com.allets.backend.data.server.entity.common.Emp;
import com.allets.backend.data.server.utils.JsonUtil;
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
public class EmpRepositoryImplTest {

	final Logger log = LoggerFactory.getLogger(EmpRepositoryImplTest.class);
	
	
	@Autowired
	EmpRepository repository;

	/**
	 * Test Save (Insert or Delete).
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void save() throws Exception {

		Emp emp = new Emp();
		emp.setEmpno(5);
		emp.setEname("clay-2");
		emp.setDeptno(5);
		
		Emp result = repository.save(emp);
		assertNotNull(result);
		log.info(JsonUtil.marshallingJsonWithPretty(result));
	}

	/**
	 * Test Remove.
	 *
	 * @throws Exception
	 *             the exception
	 */
//	@Test
	public void remove() throws Exception {

		Emp emp = new Emp();
		emp.setEname("clay");
		emp.setDeptno(5);

		Emp result = repository.save(emp);
		repository.delete(result.getEmpno());

		result = repository.findOne(result.getEmpno());
		
		assertNull(result);
		
	}

	
//	@Test
	public void findListByDeptno() throws Exception {
		
		Page<EmpDeptResult> results = repository.findListByDeptno();
		log.info(JsonUtil.marshallingJson(results));
	}
}
