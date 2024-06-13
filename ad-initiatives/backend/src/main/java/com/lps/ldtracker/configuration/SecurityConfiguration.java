package com.lps.ldtracker.configuration;

import static com.lps.ldtracker.permission.Permission.ADMIN_CREATE;
import static com.lps.ldtracker.permission.Permission.ADMIN_DELETE;
import static com.lps.ldtracker.permission.Permission.ADMIN_READ;
import static com.lps.ldtracker.permission.Permission.ADMIN_UPDATE;
import static com.lps.ldtracker.permission.Permission.USER_CREATE;
import static com.lps.ldtracker.permission.Permission.USER_DELETE;
import static com.lps.ldtracker.permission.Permission.USER_READ;
import static com.lps.ldtracker.permission.Permission.USER_UPDATE;
import static com.lps.ldtracker.security.RoleSecurity.ADMIN;
import static com.lps.ldtracker.security.RoleSecurity.GUEST;
import static com.lps.ldtracker.security.RoleSecurity.USER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JwtAuthenticationFilterConfiguration jwtAuthenticationFilterConfiguration;
	private final AuthenticationProvider authenticationProvider;
	@Value("${spring.cors.origins}")
	private List<String> origins;
	@Value("${spring.cors.methods}")
	private List<String> methods;
	@Value("${spring.cors.headers}")
	private List<String> headers;
	private static final String[] WHITE_LIST_URL = {
		"/v1/api-docs",
		"/api/health",
		"/api/v1/authentication/**",
		"/api/v1/forgot-password/**",
		"api/v1/admin/**",
		"/api/v1/resources/**",
		"/api/v1/trainings/**",
		"/api/v1/skills/**",
 		"/actuator/**",
		"/api/v1/approver/**",
 		"/api/v1/reports/**",
 		"/api/v1/users/**"
 		
    };
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.cors(httpSecurityCorsConfigurer -> {
                httpSecurityCorsConfigurer.configurationSource(request -> {
                    var cors = new org.springframework.web.cors.CorsConfiguration();
                    cors.setAllowedOrigins(origins); // Change to your frontend origin
                    cors.setAllowedMethods(methods);
                    cors.setAllowedHeaders(headers);
                    cors.setAllowCredentials(true);
                    return cors;
                });
            })
			.csrf(AbstractHttpConfigurer::disable)
			.headers(httpSecurityHeadersConfigurer -> {
			    httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
			    httpSecurityHeadersConfigurer.frameOptions().sameOrigin();
			}) 
			.authorizeHttpRequests(request -> request
				.requestMatchers(WHITE_LIST_URL)
				.permitAll()
				.requestMatchers("/api/v1/resources/**")
				.hasAnyAuthority(ADMIN.name(), USER.name())
				.requestMatchers("/api/v1/trainings/**")
				.hasAnyAuthority(ADMIN.name(), USER.name())
				.requestMatchers(GET, "/api/v1/admin/**")
				.hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                .requestMatchers(POST, "/api/v1/admin/**")
                .hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                .requestMatchers(PUT, "/api/v1/admin/**")
                .hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                .requestMatchers(DELETE, "/api/v1/admin/**")
                .hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())
                .requestMatchers("/api/v1/**")
				.hasAnyRole(GUEST.name())
                .anyRequest()
                .authenticated()
			)
			.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilterConfiguration, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
	
	
}