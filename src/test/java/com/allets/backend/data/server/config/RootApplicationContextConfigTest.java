package com.allets.backend.data.server.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootApplicationContextConfig.class, JPAConfig.class})
public class RootApplicationContextConfigTest {

	final static org.slf4j.Logger log = LoggerFactory
			.getLogger(RootApplicationContextConfigTest.class);

	
	@Autowired
	@Qualifier("commonDataSource")
	DataSource datasource;
	
	
	@Test
	public void testLoadContext() throws Exception {
		assertNotNull(datasource);
	}
	
}
