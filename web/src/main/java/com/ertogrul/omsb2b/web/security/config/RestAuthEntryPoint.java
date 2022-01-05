package com.ertogrul.omsb2b.web.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Autowired
    HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        System.out.println("Came here ");
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//        handlerExceptionResolver.resolveException(httpServletRequest,httpServletResponse,null,e);
    }



    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
       handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
    }
}
