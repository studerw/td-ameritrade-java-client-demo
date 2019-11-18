package com.studerw.tda.demo.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

	private Map<String, List<String>> roleMap;

	public UserDetailsService() {
		LOGGER.info("Creating UserDetailsService - should only see me once");
		initRoleMap();
	}

	private void initRoleMap() {
		Map<String, List<String>> tempMap = new HashMap<>();
		tempMap.put("tda-.*", Arrays.asList("ROLE_USER"));
		tempMap.put("tda-admin.*", Arrays.asList("ROLE_ADMIN"));
		this.roleMap = Collections.unmodifiableMap(tempMap);
	}

	private List<String> rolesByNamePattern(String username) {
		LOGGER.info("Checking user: {} for roles...", username);
		return this.roleMap.keySet().stream().
			filter(pattern -> username.matches(pattern)).
			map(key -> this.roleMap.get(key)).
			flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Searching for user: {}", username);
		final List<String> roles = rolesByNamePattern(username);
		if (roles.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}

		return new User(username, "",
			AuthorityUtils.createAuthorityList(roles.stream().toArray(String[]::new)));
	}

}