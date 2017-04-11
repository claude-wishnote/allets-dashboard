package com.allets.backend.data.server.repository.common;

import com.allets.backend.data.server.config.RootApplicationContextConfig;
import com.allets.backend.data.server.data.result.UserResult;
import com.allets.backend.data.server.utils.JsonUtil;
import com.allets.backend.data.server.config.JPAConfig;
import com.allets.backend.data.server.entity.common.UserReport;
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

import java.util.List;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootApplicationContextConfig.class, JPAConfig.class})
@TransactionConfiguration(transactionManager = "commonTxManager", defaultRollback = true)
@Transactional
public class UserReportRepositoryImplTest {

	final Logger log = LoggerFactory.getLogger(UserReportRepositoryImplTest.class);
	
	
	@Autowired
	UserReportRepository repository;

	/**
	 * Test Save (Insert or Delete).
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void save() throws Exception {

        UserReport userReport = new UserReport();
        userReport.setReportedUid(1l);
        userReport.setReportType("RT006");
        userReport.setReportedUid(1l);


        UserReport result = repository.save(userReport);
		assertNotNull(result);
		log.info(JsonUtil.marshallingJsonWithPretty(result));
	}


    /**
     * Test select
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void findUserReported() throws Exception {
        List<UserReport> reportList = repository.findByReportedUid(175924);
        assertNotNull(reportList);
        log.info(JsonUtil.marshallingJsonWithPretty(reportList));
    }


    /**
     * Test select
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void findAllReportedUser() throws Exception {
        Page<UserResult> reportList = repository.findAllReportedUser(0,20,null,null,null);
        assertNotNull(reportList);
        log.info(JsonUtil.marshallingJsonWithPretty(reportList));
    }


}
