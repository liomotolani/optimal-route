package com.klasha.logistics.config;


import com.klasha.logistics.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            String username = null;
            String authToken = null;

            if (header != null && header.startsWith("Bearer")) {
                authToken = header.replace("Bearer ", "");
                try {
                    username = tokenProvider.getUsernameFromJWTToken(authToken);
                    log.info("{}", username);
                } catch (IllegalArgumentException e) {
                    log.error("An error has occurred while fetching username from token", e);
                    throw new IllegalArgumentException(e.getMessage());
                } catch (ExpiredJwtException e) {
                    log.warn("The token has expired", e);
                    throw new JwtException(e.getMessage());
                } catch (SignatureException e) {
                    log.error("Authentication failed. Username or password not valid");
                    throw new JwtException(e.getMessage());
                }
            } else {
                log.warn("Couldn't find bearer string header will be ignored");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                log.info("User details --> {}", userDetails);
                if (tokenProvider.validateJWTToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                            tokenProvider.getAuthenticationToken(authToken,
                                    SecurityContextHolder.getContext().getAuthentication(),
                                    userDetails);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user " + username + " setting security context");

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    setUserDetailsInTokenProvider(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
            log.info("{}",ex.getMessage());
            throw new BadRequestException(ex.getMessage());
        }
    }

    private void setUserDetailsInTokenProvider(String authToken) {
        tokenProvider.setDetails(authToken);
    }

}
