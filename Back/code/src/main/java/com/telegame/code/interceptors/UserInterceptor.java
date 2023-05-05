package com.telegame.code.interceptors;

import com.telegame.code.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class UserInterceptor implements HandlerInterceptor {
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS")) return true;

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return true;
        throw new ResponseStatusException(HttpStatus.LOCKED);
    }
}