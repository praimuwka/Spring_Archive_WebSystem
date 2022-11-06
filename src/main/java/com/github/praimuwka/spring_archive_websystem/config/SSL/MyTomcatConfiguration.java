package com.github.praimuwka.spring_archive_websystem.config.SSL;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MyTomcatConfiguration {
    //  Add new connectors to Tomcat
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sslConnectorCustomizer() {
        return (tomcat) -> tomcat.addAdditionalTomcatConnectors(createConnectorConnector());
    }

    //  Enable redirect from http to https
    private Connector createConnectorConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        return connector;
    }

}