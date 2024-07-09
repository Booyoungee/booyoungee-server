package com.server.booyoungee.global.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
		return server -> {
			server.addAdditionalTomcatConnectors(createHttpConnector(8080));
			server.addAdditionalTomcatConnectors(createHttpConnector(8282));
		};
	}

	private Connector createHttpConnector(int port) {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(port);  // 설정한 포트 사용
		connector.setSecure(false);
		connector.setRedirectPort(8443);  // HTTPS 포트로 리디렉션
		return connector;
	}
}
