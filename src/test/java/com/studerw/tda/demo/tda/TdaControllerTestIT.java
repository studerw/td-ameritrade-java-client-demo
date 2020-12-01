package com.studerw.tda.demo.tda;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.studerw.tda.demo.mvc.ControllerTest;
import com.studerw.tda.model.instrument.Query;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

public class TdaControllerTestIT extends ControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(TdaControllerTestIT.class);

	@Test
	public void shouldReturnQuotes() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get(this.apiPrefix+"/tda/quotes")
			.accept(MediaType.APPLICATION_JSON)
			.param("symbols", "msft", "vtsax", "infy", "f"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(4)))
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		LOGGER.debug(response.getContentAsString());
	}

	@Test
	public void shouldReturnPriceHistory() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get(this.apiPrefix+"/tda/priceHistory")
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
		final MvcResult mvcResult = this.mockMvc.perform(get(this.apiPrefix+"/tda/accounts")
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
		final MvcResult mvcResult = this.mockMvc.perform(get(this.apiPrefix+"/tda/accounts")
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
		final MvcResult mvcResult = this.mockMvc.perform(get(this.apiPrefix+"/tda/instruments")
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
		final MvcResult mvcResult = this.mockMvc.perform(get(this.apiPrefix+"/tda/fundamentals")
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
