package com.studerw.tda.demo.config;

import java.util.Properties;

import com.studerw.tda.client.HttpTdaClient;
import com.studerw.tda.client.TdaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
	private Environment env;

	@Bean
	public TdaClient getTdaClient(Environment environment) {
		LOGGER.info("Instantiating TdaClient - should only see me once");
		Properties props = getProps();
		return new HttpTdaClient(props);
	}

	private Properties getProps() {
		Properties props = new Properties();
		props.setProperty("tda.token.refresh", this.env.getRequiredProperty("tda.token.refresh"));
		props.setProperty("tda.client_id", this.env.getRequiredProperty("tda.client_id"));
		props.setProperty("tda.debug.bytes.length", this.env.getRequiredProperty("tda.debug.bytes.length"));
		props.setProperty("tda.url", this.env.getRequiredProperty("tda.url"));
		return props;
	}

	@Autowired
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}
}


// 	@Bean
// 	public UserDetailsService userDetailsService() {
// 		return new UserDetailsService() {
// 			@Override
// 			public UserDetails loadUserByUsername(String username) {
// 				return new User(username, "",
// 					AuthorityUtils
// 						.commaSeparatedStringToAuthorityList("ROLE_USER"));
// 			}
// 		};
// 	}

