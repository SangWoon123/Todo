package com.gdsc.todo.global.token;

import com.gdsc.todo.global.error.ErrorResponse;
import com.sun.jdi.request.InvalidRequestStateException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.jar.JarException;

@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch (InvalidRequestStateException e){
            setErrorResponse(HttpStatus.UNAUTHORIZED,response,e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        ErrorResponse errorResponse=ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .code(401)
                .build();

        response.getWriter().print(errorResponse.converToJson());
    }
}
