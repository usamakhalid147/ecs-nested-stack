package com.acima.genesiscreditservice.security;

import com.acima.genesiscreditservice.model.ErrorResponse;
import com.acima.genesiscreditservice.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Auth token filter.
 * @author LavKumar
 */
public class AuthTokenFilter implements Filter {

    private final List<String> allowedUrls = List.of("/api/credit/process-qr-code-data");

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        Optional<String> matchedUrl = allowedUrls.stream()
                .filter(url -> url.equals(httpRequest.getRequestURI()))
                .findFirst();

        if (matchedUrl.isPresent()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (StringUtils.isNotBlank(httpRequest.getHeader("Authorization"))
                    && jwtUtil.isTokenValid(httpRequest)) {
                // Header is valid, proceed with the request
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                // Header is missing or invalid, deny access
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        new Date(),
                        "Please provide valid authentication token", "Invalid Credentials")));
            }
        }
    }


}
