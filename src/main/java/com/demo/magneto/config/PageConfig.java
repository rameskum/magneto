package com.demo.magneto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		String dashboardU = "/dashboard";
		registry.addViewController(dashboardU).setViewName("dashboard");
		registry.addRedirectViewController("/index", dashboardU);
		registry.addRedirectViewController("/", dashboardU);
		registry.addViewController("/profile").setViewName("profile");
	}
}
