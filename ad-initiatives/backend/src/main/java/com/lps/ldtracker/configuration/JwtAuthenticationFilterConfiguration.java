package com.lps.ldtracker.configuration;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lps.ldtracker.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterConfiguration extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
    	@NonNull HttpServletRequest httpServletRequest, 
    	@NonNull HttpServletResponse httpServletResponse, 
    	@NonNull FilterChain filterChain
    ) throws ServletException, IOException {
    	if (httpServletRequest.getServletPath().contains("/api/v1/authentication")) {
    		filterChain.doFilter(httpServletRequest, httpServletResponse);
    		return;
    	}
    	
    	final String authHeader = httpServletRequest.getHeader("Authorization");
        final String jwt;
        final String username;
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	filterChain.doFilter(httpServletRequest, httpServletResponse);
        	return;
        }
        
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = this.userDetailsService
        		.loadUserByUsername(username);
        	
        	if (jwtService.isTokenValid(jwt, userDetails)) {
        		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
    				userDetails, 
    				null,
    				userDetails.getAuthorities()
    			);
        		usernamePasswordAuthenticationToken
        			.setDetails(
        				new WebAuthenticationDetailsSource()
        					.buildDetails(httpServletRequest)
        			);
        		SecurityContextHolder
        			.getContext()
        			.setAuthentication(usernamePasswordAuthenticationToken);
        	}
        }
        
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}