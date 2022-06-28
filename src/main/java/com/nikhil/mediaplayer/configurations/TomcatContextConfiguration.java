package com.nikhil.mediaplayer.configurations;

import java.io.File;

import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatContextConfiguration {
    
    @Bean
    @ConditionalOnProperty(name = "song.directory.path")
    public TomcatServletWebServerFactory servletContainerFactory(@Value("${song.directory.path}") String path,
            @Value("${song.directory.context-path}") String contextPath) {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                new File(tomcat.getServer().getCatalinaBase(), "webapps").mkdirs();
                tomcat.addWebapp(path, contextPath);
                return super.getTomcatWebServer(tomcat);
            }
        };
    }
}
