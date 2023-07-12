package com.acima.security;

import com.acima.model.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * The type Auth entry point jwt.
 * @author LavKumar
 */
@Component
public class AuthEntryPointJwt implements ServerAuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");

		String invalid_credentials = "";
		try {
			invalid_credentials = new ObjectMapper().writeValueAsString(new ErrorResponse(
					HttpServletResponse.SC_UNAUTHORIZED,
					new Date(),
					e.getMessage(), "Invalid Credentials"));
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
		return response.writeWith(Mono.just(response.bufferFactory().wrap(invalid_credentials.getBytes())));
	}

}