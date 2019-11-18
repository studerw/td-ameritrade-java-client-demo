package com.studerw.tda.demo.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class UserDetailsServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);
	private static UserDetailsService userDetailsService = new UserDetailsService();

	@Test
	public void testClient() {
		String user = "tda-client-1";
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user);
		userDetails.getAuthorities().stream().forEach(a -> LOGGER.debug("{}",a));

		assertThat(userDetails.isEnabled()).isTrue();
		assertThat(userDetails.getAuthorities()).size().isEqualTo(1);
		assertThat(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
	}

	@Test
	public void testAdmin() {
		String user = "tda-admin";
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user);
		userDetails.getAuthorities().stream().forEach(a -> LOGGER.debug("{}",a));
		assertThat(userDetails.isEnabled()).isTrue();
		assertThat(userDetails.getAuthorities()).size().isEqualTo(2);
		assertThat(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
		assertThat(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));

	}

	@Test(expected = UsernameNotFoundException.class)
	public void testBadUser() {
		String user = "foobar";
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user);
		fail("Should not get here");

	}

	@Test(expected = UsernameNotFoundException.class)
	public void testBadUser2() {
		String user = "";
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user);
		fail("Should not get here");

	}

}