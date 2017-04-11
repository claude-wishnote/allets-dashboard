package com.allets.backend.data.server.config;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * 최상위 스프링 컨텍스트 .
 */
@Configuration
@Import(value = {DefaultPropertyConfig.class,
        DatasourceConfig.class, JPAConfig.class, SecurityConfig.class, RedisConfig.class, AWSUploadConfig.class, EmailConfig.class, VelocityEngineConfig.class, JSONDocConfig.class
        , ElasticSearchConfig.class})
@ComponentScan(
        basePackages = {"com.allets.backend.ums.server.facade", "com.allets.backend.ums.server.service"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)})
@EnableAspectJAutoProxy
public class RootApplicationContextConfig {
}
