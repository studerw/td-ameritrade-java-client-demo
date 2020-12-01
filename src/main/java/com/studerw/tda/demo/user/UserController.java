package com.studerw.tda.demo.user;

import java.security.Principal;
import java.util.Collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/user")
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private final Environment environment;
	private final ObjectMapper objectMapper;

	public UserController(Environment environment, ObjectMapper objectMapper) {
		this.environment = environment;
		this.objectMapper = objectMapper;
	}


	@GetMapping(value = "/whoami", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserPojo> whoami() {
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
