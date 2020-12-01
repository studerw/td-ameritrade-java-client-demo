package com.studerw.tda.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SpringDocConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringDocConfig.class);

	private String apiPrefix;

	public SpringDocConfig(Environment environment) {
		LOGGER.info("Instantiating SwaggerConfig - should only see me once");
		this.apiPrefix = environment.getRequiredProperty("api.prefix");
		LOGGER.info("Using prefix: {} for Swagger", this.apiPrefix);
	}

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.info(new Info().title("TD Ameritrade Client demo")
						.description("TD Ameritrade Client demo")
						.version("v1.0")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description("TDA API Docs")
						.url("http://td-ameritrade-client.studerw.com.s3-website-us-east-1.amazonaws.com/"));
	}

}
