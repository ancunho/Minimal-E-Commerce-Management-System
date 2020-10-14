package com.ahnstudio.management;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }


//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addConnectorCustomizers(new GwsTomcatConnectionCustomizer());
//        return tomcat;
//    }
//
//    public class GwsTomcatConnectionCustomizer implements TomcatConnectorCustomizer {
//
//        public GwsTomcatConnectionCustomizer() {
//        }
//
//        @Override
//        public void customize(Connector connector) {connector.setScheme("http");
//            connector.setPort(9001);
//            connector.setSecure(false);
//            connector.setRedirectPort(443);
//        }
//    }


}
