package com.allets.backend.dashboard.server.config;

import com.allets.backend.dashboard.server.ApiConstants;
import com.allets.backend.dashboard.server.jdbc.CustomRoutingDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * DBCP 설정 (로컬 개발 환경에서만 사용 한다) .
 */
@Configuration
public class DatasourceConfig {

	/**
	 * The constant log.
	 */
	final static Logger log = LoggerFactory.getLogger(DatasourceConfig.class);

	/**
	 * The Mysql driver.
	 */
	@Value("${backend.dashboard.mysql.driver}")
	String mysqlDriver;

	/**
	 * The Default auto commit.
	 */
	@Value("${backend.dashboard.db.auto.commit}")
	String defaultAutoCommit;

	/**
	 * The Common write dBURL.
	 */
	@Value("${backend.dashboard.common.db.url}")
	String commonDBURL;
	@Value("${backend.dashboard.common.db.user}")
	String commonDBUser;
	@Value("${backend.dashboard.common.db.password}")
	String commonDBPassword;
	@Value("${backend.dashboard.common.db.initialsize}")
	String commonInitalSize;
	@Value("${backend.dashboard.common.db.maxtotal}")
	String commonMaxTotal;
	@Value("${backend.dashboard.common.db.maxidle}")
	String commonMaxIdle;
	@Value("${backend.dashboard.common.db.minidle}")
	String commonMinIdle;
    @Value("${backend.dashboard.common.db.validationQuery}")
    String commonValidationQuery;
    @Value("${backend.dashboard.common.db.testOnBorrow}")
    Boolean commonTestOnBorrow;
	//@Value("${backend.dashboard.common.db.minevictableidletime}")
	//String commonMinEvictableIdleTime;
	@Value("${backend.dashboard.common.db.timeBetweenEvictionRunsMillis}")
	String commonTimeBetweenEvictionRunsMillis;
	@Value("${backend.dashboard.common.db.removeAbandoned}")
	String commonRemoveAbandoned;
	@Value("${backend.dashboard.common.db.removeAbandonedTimeout}")
	String commonRemoveAbandonedTimeout;
	@Value("${backend.dashboard.common.db.logAbandoned}")
	String commonLogAbandoned;

	/**
	 *  read dBURL.
	 */
	@Value("${backend.dashboard.read.db.url}")
	String readDBURL;
	@Value("${backend.dashboard.read.db.user}")
	String readDBUser;
	@Value("${backend.dashboard.read.db.password}")
	String readDBPassword;
	@Value("${backend.dashboard.read.db.initialsize}")
	String readInitalSize;
	@Value("${backend.dashboard.read.db.maxtotal}")
	String readMaxTotal;
	@Value("${backend.dashboard.read.db.maxidle}")
	String readMaxIdle;
	@Value("${backend.dashboard.read.db.minidle}")
	String readMinIdle;
    @Value("${backend.dashboard.read.db.validationQuery}")
    String readValidationQuery;
    @Value("${backend.dashboard.read.db.testOnBorrow}")
    Boolean readTestOnBorrow;
	//@Value("${backend.dashboard.read.db.minevictableidletime}")
	//String readMinEvictableIdleTime;
	@Value("${backend.dashboard.read.db.timeBetweenEvictionRunsMillis}")
	String readTimeBetweenEvictionRunsMillis;
	@Value("${backend.dashboard.read.db.removeAbandoned}")
	String readRemoveAbandoned;
	@Value("${backend.dashboard.read.db.removeAbandonedTimeout}")
	String readRemoveAbandonedTimeout;
	@Value("${backend.dashboard.read.db.logAbandoned}")
	String readLogAbandoned;
	/**
	 * common datasource
	 *
	 * @return the data source
	 */
	@Bean(destroyMethod = "close")
	public DataSource rawCommonDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(mysqlDriver);
		dataSource.setUrl(commonDBURL);
		dataSource.setUsername(commonDBUser);
		dataSource.setPassword(commonDBPassword);

		dataSource.setInitialSize(Integer.valueOf(commonInitalSize));
		dataSource.setMaxIdle(Integer.valueOf(commonMaxIdle));
		dataSource.setMaxTotal(Integer.valueOf(commonMaxTotal));
		dataSource.setMinIdle(Integer.valueOf(commonMinIdle));
        dataSource.setValidationQuery(commonValidationQuery);
        dataSource.setTestOnBorrow(commonTestOnBorrow);
		//dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(commonMinEvictableIdleTime));
		dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(commonTimeBetweenEvictionRunsMillis));
        //dataSource.setRemoveAbandonedOnMaintenance(Boolean.valueOf(commonRemoveAbandoned));
		dataSource.setRemoveAbandonedOnBorrow(Boolean.valueOf(commonRemoveAbandoned));
		dataSource.setRemoveAbandonedTimeout(Integer.valueOf(commonRemoveAbandonedTimeout));
 		dataSource.setLogAbandoned(Boolean.valueOf(commonLogAbandoned));
		dataSource.setDefaultAutoCommit(Boolean.valueOf(defaultAutoCommit));

		return dataSource;
	}

	/**
	 * read datasource
	 * @return
	 */
	@Bean(destroyMethod = "close")
	public DataSource rawReadDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(mysqlDriver);
		dataSource.setUrl(readDBURL);
		dataSource.setUsername(readDBUser);
		dataSource.setPassword(readDBPassword);

		dataSource.setInitialSize(Integer.valueOf(readInitalSize));
		dataSource.setMaxIdle(Integer.valueOf(readMaxIdle));
		dataSource.setMaxTotal(Integer.valueOf(readMaxTotal));
		dataSource.setMinIdle(Integer.valueOf(readMinIdle));
        dataSource.setValidationQuery(readValidationQuery);
        dataSource.setTestOnBorrow(readTestOnBorrow);
		//dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(readMinEvictableIdleTime));
		dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(readTimeBetweenEvictionRunsMillis));
        //dataSource.setRemoveAbandonedOnMaintenance(Boolean.valueOf(readRemoveAbandoned));
		dataSource.setRemoveAbandonedOnBorrow(Boolean.valueOf(readRemoveAbandoned));
		dataSource.setRemoveAbandonedTimeout(Integer.valueOf(readRemoveAbandonedTimeout));
		dataSource.setLogAbandoned(Boolean.valueOf(readLogAbandoned));
		dataSource.setDefaultAutoCommit(Boolean.valueOf(defaultAutoCommit));

		return dataSource;
	}

	@Bean
 	public DataSource routingDataSource() {
		CustomRoutingDataSource routingDataSource = new CustomRoutingDataSource();

		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(ApiConstants.DATASOURCE_TYPE_COMMON, rawCommonDataSource());
		targetDataSources.put(ApiConstants.DATASOURCE_TYPE_READ, rawReadDataSource());

		routingDataSource.setDefaultTargetDataSource(rawCommonDataSource());
		routingDataSource.setTargetDataSources(targetDataSources);

		return routingDataSource;
	}

	/**
	 * LazyConnectionDataSourceProxy
	 *
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		return new LazyConnectionDataSourceProxy(routingDataSource());
	}

	/**
     * Datasource Proxy for SQL Log.
     *
     * @return the data source
     */
    @Bean
    public DataSource commonDataSource() {

        Log4jdbcProxyDataSource proxyDataSource = new Log4jdbcProxyDataSource(
				dataSource());
        Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
        formatter.setLoggingType(LoggingType.MULTI_LINE);
        formatter.setSqlPrefix("SQL:::");
        proxyDataSource.setLogFormatter(formatter);

        return proxyDataSource;
    }
   
	
}
