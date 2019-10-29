package com.studerw.tda.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

	public UserDetailsService() {
		LOGGER.info("Creating UserDetailsService - should only see me once");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Creating new User: {}", username);
		return new User(username, "", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
	}
}