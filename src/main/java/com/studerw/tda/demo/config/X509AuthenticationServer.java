package com.studerw.tda.demo.config;

import com.studerw.tda.demo.user.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class X509AuthenticationServer extends WebSecurityConfigurerAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(X509AuthenticationServer.class);

//	public X509AuthenticationServer() {
//		super();
//		LOGGER.info("Creating X509AuthenticationService - should only see me once");
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
			.anyRequest().authenticated()
			.and()
			.x509()
			.subjectPrincipalRegex("CN=(.*?)(?:,|$)")
			.userDetailsService(new UserDetailsService());
	}
}