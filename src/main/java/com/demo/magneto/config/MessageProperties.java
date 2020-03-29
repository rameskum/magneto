package com.demo.magneto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:message.properties")
public class MessageProperties {

	@Autowired
	private Environment env;

	public String getConfigValue(String configKey) {
		return env.getProperty(configKey);
	}
}
