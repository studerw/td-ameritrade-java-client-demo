package com.studerw.tda.demo.user;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.studerw.tda.demo.mvc.ControllerTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserControllerTest extends ControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get(this.apiPrefix+"/user/whoami"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username", equalTo("tda-client-test")));
	}
}
