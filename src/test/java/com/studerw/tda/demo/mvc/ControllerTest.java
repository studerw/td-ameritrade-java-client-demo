package com.studerw.tda.demo.mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class ControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testContext() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	public void errorTest() throws Exception {
		final MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/tda/quotes")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("$.message").isNotEmpty())
			.andReturn();

		final MockHttpServletResponse response = mvcResult.getResponse();
		LOGGER.debug(response.getContentAsString());
	}
}
