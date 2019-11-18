package com.studerw.tda.demo.tda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.studerw.tda.model.instrument.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser("tda-client-test")
@ActiveProfiles("test")
public class TdaControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(TdaControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testContext() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/api/v1/tda/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Hello")));
	}

	@Test
	public void shouldReturnQuotes() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/tda/quotes")
			.accept(MediaType.APPLICATION_JSON)
			.param("symbols", "msft", "vtsax", "s", "f"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(4)))
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		LOGGER.debug(response.getContentAsString());
	}

	@Test
	public void shouldReturnPriceHistory() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/tda/priceHistory")
			.accept(MediaType.APPLICATION_JSON)
			.param("symbol", "msft"))
			.andExpect(status().isOk())
//			.andExpect(jsonPath("$", hasSize(4)))
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		LOGGER.debug(response.getContentAsString());
	}

	@Test
	public void shouldReturnAccountsNoPositions() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/tda/accounts")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].positions").doesNotExist())
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		String json = response.getContentAsString();
		LOGGER.debug(json);
	}

	@Test
	public void shouldReturnAccountsWithPositions() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/tda/accounts")
			.accept(MediaType.APPLICATION_JSON)
			.param("positions", "true"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].positions").exists())
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		String json = response.getContentAsString();
		LOGGER.debug(json);
	}

	@Test
	public void shouldReturnInstruments() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/tda/instruments")
			.param("searchStr", "m.*")
			.param("queryType", Query.QueryType.SYMBOL_REGEX.name())
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[*].symbol").isNotEmpty())
			.andExpect(jsonPath("$.[*].cusip").isNotEmpty())
			.andExpect(jsonPath("$.[*].description").isNotEmpty())
			.andExpect(jsonPath("$.[*].exchange").isNotEmpty())
			.andExpect(jsonPath("$.[*].assetType").isNotEmpty())
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		String json = response.getContentAsString();
		LOGGER.debug(json);
	}

	@Test
	public void shouldReturnFundamentals() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/tda/fundamentals")
			.param("symbol", "msft")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.symbol").isNotEmpty())
			.andExpect(jsonPath("$.cusip").isNotEmpty())
			.andExpect(jsonPath("$.description").isNotEmpty())
			.andExpect(jsonPath("$.exchange").isNotEmpty())
			.andExpect(jsonPath("$.assetType").isNotEmpty())
			.andExpect(jsonPath("$.fundamental.high52").isNotEmpty())
			.andExpect(jsonPath("$.fundamental.low52").isNotEmpty())
			.andExpect(jsonPath("$.fundamental.dividendDate").isNotEmpty())
			.andExpect(jsonPath("$.fundamental.dividendAmount").isNotEmpty())
			.andExpect(jsonPath("$.fundamental.dividendYield").isNotEmpty())
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		String json = response.getContentAsString();
		LOGGER.debug(json);
	}

}
