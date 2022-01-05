package com.ertogrul.omsb2b.web.security.jwt;

import com.ertogrul.omsb2b.common.spring.SpringContext;
import com.ertogrul.omsb2b.service.services.TokenBlockListService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {


    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_TOKEN="wsauth_token"; //this is for websocket handshake as there is no current possibility  to use Authorization header with sockjs
    //refer to this doc https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp-authentication
    // issue https://github.com/sockjs/sockjs-client/issues/196

    private JwtTokenProvider tokenProvider;

    private TokenBlockListService tokenBlockListService;

    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.tokenBlockListService= SpringContext.getBean(TokenBlockListService.class);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt) && !tokenBlockListService.contains(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        String jwt = request.getParameter(AUTHORIZATION_TOKEN);
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }

}
