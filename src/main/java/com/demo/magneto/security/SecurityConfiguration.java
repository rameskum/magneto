package com.demo.magneto.security;

import com.demo.magneto.repositories.RememberMeRepository;
import com.demo.magneto.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static com.demo.magneto.security.ApplicationUserPermissions.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({AdminProperties.class})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private RememberMeRepository rememberMeRepository;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("./data/pilot/**", "/actuator", "/actuator/**", "/auth/**", "/configuration/security",
				"/configuration/ui", "/css/**", "/favicon.ico", "/favicon.png", "/fonts/**", "/images/**", "/img/**", "/js/**",
				"/resources/**", "/resources/static/**", "/scss/**", "/static/**", "/swagger-resources/**", "/swagger-ui.html",
				"/swagge‌​r-ui.html", "/v2/api-docs", "/vendor/**", "/webjars/**");
	}

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.headers()
				.frameOptions()
					.sameOrigin()
			.and()
			.cors()
			.and()
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers( "/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/error/**").permitAll()
				.antMatchers("/admin/**").hasRole(ApplicationUserRoles.ADMIN.name())
				.antMatchers(HttpMethod.GET, "/configure/**").hasAnyAuthority(CONFIG_READ.name())
				.antMatchers(HttpMethod.POST, "/configure/**").hasAnyAuthority(CONFIG_UPDATE.name())
				.antMatchers(HttpMethod.DELETE, "/configure/**").hasAnyAuthority(CONFIG_DELETE.name())
				.antMatchers("/profile/**").authenticated()
			.anyRequest()
				.authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.failureUrl("/login?error=true")
				.usernameParameter("userName")
				.passwordParameter("password")
				.successHandler(this.authenticationSuccessHandler)
			.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll()
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "remember-me")
				.logoutSuccessUrl("/login?logout=success")
			.and()
				.exceptionHandling()
					.accessDeniedPage("/error/401")
			.and()
				.rememberMe()
					.rememberMeParameter("remember-me")
					.tokenValiditySeconds(2592000)
					.tokenRepository(this.rememberMeRepository)
					.userDetailsService(this.userDetailsService);
	}
	// @formatter:on

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
		return daoAuthenticationProvider;
	}
}
