package com.studerw.tda.demo.user;

import java.security.Principal;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studerw.tda.client.TdaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private final Environment environment;

	private final TdaClient tdaClient;

	private final ObjectMapper objectMapper;

	public UserController(Environment environment, TdaClient tdaClient, ObjectMapper objectMapper) {
		this.environment = environment;
		this.tdaClient = tdaClient;
		this.objectMapper = objectMapper;
	}


	@GetMapping("/whoami")
	public ResponseEntity<UserPojo> whoami(Principal principal) {
		LOGGER.info("[{}] - whoami", principal.getName());
		UserPojo userPojo = new UserPojo(SecurityContextHolder.getContext().getAuthentication());
		return ResponseEntity.ok(userPojo);
	}


	private static class UserPojo {
		private String username;
		private Collection<? extends GrantedAuthority> authorities;

		public UserPojo(Authentication authentication) {
			this.username = authentication.getName();
			this.authorities = authentication.getAuthorities();
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
		}
	}

}
