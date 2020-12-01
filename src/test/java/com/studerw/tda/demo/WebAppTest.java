package com.studerw.tda.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import com.studerw.tda.client.TdaClient;
import com.studerw.tda.model.AssetType;
import com.studerw.tda.model.quote.EquityQuote;
import com.studerw.tda.model.quote.Quote;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApp.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class WebAppTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebApp.class);

	@Autowired private Environment environment;
	@Autowired TdaClient tdaClient;

	@Test
	public void testConfig() {
		assertThat(environment).isNotNull();
		LOGGER.debug("testConfig...");
	}
}
