//package com.studerw.tda.demo.mvc;
//
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	private final UserDetailsService userDetailsService;
//
//	public SecurityConfig(UserDetailsService userDetailsService) {
//		this.userDetailsService = userDetailsService;
//	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// Note:
//		// Use this to enable the tomcat basic authentication (tomcat popup rather than spring login page)
//		// Note that the CSRf token is disabled for all requests (change it as you wish...)
//		http.csrf().disable().authorizeRequests().anyRequest().
//			http
//				.csrf().disable()
//				.authorizeRequests()
//				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.x509()
//				.subjectPrincipalRegex("CN=(.*?)(?:,|$)")
//				.userDetailsService(this.userDetailsService);
//				.and()
//				.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.and()
//				.logout()
//				.permitAll();
//	}
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		// Add here any custom code you need in order to get the credentials from the user...
//		auth.inMemoryAuthentication()
//			.withUser("admin")
//			.password("myPassword")
//			.roles("USER");
//	}
//}