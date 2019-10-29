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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class WebAppTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebApp.class);

//	@Autowired private TestRestTemplate testRestTemplate;
	@Autowired private Environment environment;
	@Autowired TdaClient tdaClient;

	@Test
	public void testConfig() {
//		assertThat(testRestTemplate).isNotNull();
		assertThat(environment).isNotNull();
		LOGGER.debug("testConfig...");
	}

	@Test
	@Ignore
	public void testTdaClient() {
		final Quote quote = tdaClient.fetchQuote("msft");
		assertThat(quote.getAssetType()).isEqualTo(AssetType.EQUITY);
		assertThat(quote).isInstanceOf(EquityQuote.class);
		assertThat(quote.getSymbol()).isEqualToIgnoringCase("msft");
		EquityQuote equityQuote = (EquityQuote) quote;
		assertThat(equityQuote).isNotNull();
		assertThat(equityQuote.getAskPrice()).isGreaterThanOrEqualTo(BigDecimal.ONE);
	}
}
