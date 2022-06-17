package com.correctin.demo.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.correctin.demo.exception.ExceptionResponse;
import com.correctin.demo.service.impl.UserDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

// This class work every request and it will validate token
@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceImpl userService;
    @Autowired
    private JWTUtil jwtUtil;

    // for ObjectMapper to format date
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // This means user want to login continue the chain.
        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if(jwt == null || jwt.isBlank()) {
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                        "Invalid JWT Token in Bearer Header",
                        FORBIDDEN.value(),
                        LocalDateTime.now()
                );
                new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .setDateFormat(df)
                        .writeValue(response.getOutputStream(),exceptionResponse);
            } else {
                try {
                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails = this.userService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            email,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );
                    if(SecurityContextHolder.getContext().getAuthentication() == null)
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                }catch(JWTVerificationException e) {
                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    ExceptionResponse exceptionResponse = new ExceptionResponse(
                            "Invalid JWT Token",
                            FORBIDDEN.value(),
                            LocalDateTime.now()
                    );
                    new ObjectMapper()
                            .registerModule(new JavaTimeModule())
                            .setDateFormat(df)
                            .writeValue(response.getOutputStream(),exceptionResponse);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
