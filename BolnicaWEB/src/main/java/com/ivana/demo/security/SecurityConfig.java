package com.ivana.demo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ivana.demo.services.AuthService;


@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authProvider,
			AuthenticationSuccessHandler successHandler) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/index", "/login", "/login.jsp", "/register", "/register.jsp", "/error",
						"/css/**", "/js/**", "/images/**", "/webjars/**")
				.permitAll()
				.requestMatchers("/lekar/**")
				.hasRole("LEKAR")
				.requestMatchers("/pacijent/**")
				.hasRole("PACIJENT")
				.requestMatchers("/admin/**")
				.hasRole("ADMIN")
				.anyRequest()
				.authenticated())
	    
				.authenticationProvider(authProvider)
				.formLogin(form -> form.loginPage("/login")
						.loginProcessingUrl("/login")
						.successHandler(successHandler)
						.failureUrl("/login?error")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout=true")
						.permitAll());
		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return (request, response, authentication) -> {
			var auths = authentication.getAuthorities();
			String ctx = request.getContextPath();

			String target = "/"; // fallback
			if (auths.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
				target = "/admin";
			} else if (auths.stream().anyMatch(a -> a.getAuthority().equals("ROLE_LEKAR"))) {
				target = "/lekar";
			} else if (auths.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PACIJENT"))) {
				target = "/pacijent";
			}
			response.sendRedirect(ctx + target);
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(PasswordEncoder encoder, AuthService authService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(authService); // <—
		provider.setPasswordEncoder(encoder); // <—
		return provider;
	}

}
