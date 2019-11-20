package com.studerw.tda.demo.config;

import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static com.google.common.base.Predicates.*;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);
	private String apiPrefix;

	public SwaggerConfig(Environment environment) {
		LOGGER.info("Instantiating SwaggerConfig - should only see me once");
		this.apiPrefix = environment.getRequiredProperty("api.prefix");
		LOGGER.info("Using prefix: {} for Swagger", this.apiPrefix);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(regex(this.apiPrefix+"/.*"))
			.build();
	}

	private Predicate<String> paths() {
		return or(regex(this.apiPrefix + ".*"));
	}
}