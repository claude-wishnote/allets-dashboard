package com.allets.backend.dashboard.server.config;

import com.allets.backend.dashboard.server.JSONDoc.JSONDocProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by claude on 2015/11/17.
 */
@Configuration
public class JSONDocConfig {

    @Value("${jsondoc.switch}")
    private boolean jsondocSwitch;

    @Value("${service.version}")
    private String version;

    @Value("${service.basePath}")
    private String basePath;
    private List<String> packages = Arrays.asList("com.allets.backend.dashboard.server.web", "com.allets.backend.dashboard.server.data", "com.allets.backend.dashboard.server.entity.common");


    @Bean
    public JSONDocProperty jsonDocProperty() {
        JSONDocProperty jsonDocProperty = new JSONDocProperty();
        jsonDocProperty.setBasePath(basePath);
        jsonDocProperty.setVersion(version);
        jsonDocProperty.setJsondocSwitch(jsondocSwitch);
        jsonDocProperty.setPackages(packages);
        return jsonDocProperty;
    }
}
