package com.studerw.tda.demo.mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser("tda-client-test")
@ActiveProfiles("test")
public class ControllerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest.class);

	@Rule
	public JUnitRestDocumentation restDocumentation =
		new JUnitRestDocumentation("target/generated-snippets");

	protected MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext context;

	@Before
	public void setUp() {
		assertThat(this.context).isNotNull();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
			.apply(documentationConfiguration(this.restDocumentation))
			.apply(springSecurity())
			.alwaysDo(document("{method-name}",
				preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
			.build();
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
