package com.allets.backend.data.server.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;

/**
 * Created by claude on 2015/10/19.
 */
public class VelocityEngineConfig {

    @Bean
    VelocityEngine velocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.setProperty("input.encoding", "UTF-8");
        velocityEngine.setProperty("output.encoding", "UTF-8");
        velocityEngine.setProperty("eventhandler.include.class", "org.apache.velocity.app.event.implement.IncludeRelativePath");
        velocityEngine.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
        velocityEngine.init();
        return velocityEngine;
    }
}
