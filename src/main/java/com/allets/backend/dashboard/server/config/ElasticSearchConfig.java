package com.allets.backend.dashboard.server.config;

import com.allets.backend.dashboard.server.elasticsearch.ElasticSearchTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by claude on 2015/12/17.
 */

@Configuration
public class ElasticSearchConfig {
    @Value("${elasticsearch.host}")
    String elasticSearchHost;
    @Value("${elasticsearch.transportclient.port}")
    String transportClientPort;
    @Value("${elasticsearch.cluster.name}")
    String elasticSearchClusterName;
    @Value("${elasticsearch.switch}")
    String elasticSearchSwitch;

    @Bean
    public ElasticSearchTemplate elasticsearch() throws Exception {
        ElasticSearchTemplate elasticSearchTemplate = new ElasticSearchTemplate(elasticSearchHost, Integer.valueOf(transportClientPort),elasticSearchClusterName,Boolean.valueOf(elasticSearchSwitch));
        return elasticSearchTemplate;
    }
}
