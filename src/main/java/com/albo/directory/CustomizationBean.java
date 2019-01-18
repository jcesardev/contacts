package com.albo.directory;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>{

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.setContextPath("/directory");
		factory.setPort(80);
	}
	
}
