package com.ertogrul.omsb2b.web.security.jwt;


import com.ertogrul.omsb2b.service.security.ManagerPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private static final String MENUS_KEY = "menus";

    private static final String SUPER_ADMIN="managerSuperAdmin";

    private static final String FIRST_NAME="firstName";

    private static final String LAST_NAME="lastName";


    private Key key;

    @Value("${alovtech.security.authentication.jwt.token-validity-in-seconds}")
    private long tokenValidityInMilliseconds;

    @Value("${alovtech.security.authentication.jwt.token-validity-in-seconds-for-remember-me}")
    private long tokenValidityInMillisecondsForRememberMe;

    @Value("${alovtech.security.authentication.jwt.base64-secret}")
    private String base64Secret;

    public JwtTokenProvider() {

    }


    @PostConstruct
    public void init() {
        byte[] keyBytes;

            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(base64Secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds =
                1000 *60* tokenValidityInMilliseconds;
        this.tokenValidityInMillisecondsForRememberMe =
                1000 *60* tokenValidityInMillisecondsForRememberMe;
    }




    public String createToken(Authentication authentication, boolean rememberMe) {
       //we insert shop id to token as claim
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        final ManagerPrincipal principal = (ManagerPrincipal) authentication.getPrincipal();
        Map<String,Object> claims=new HashMap<>();
        claims.put(MENUS_KEY,authorities);
        claims.put(SUPER_ADMIN,principal.isSuperAdmin());
        claims.put(FIRST_NAME,principal.getFirstName());
        claims.put(LAST_NAME,principal.getLastName());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }



    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody();
        final String[] split = claims.get(MENUS_KEY).toString().split(",");
        Collection<? extends GrantedAuthority> authorities;
        if(split.length>0 && !split[0].isEmpty()) {
        authorities = Arrays.stream(split)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
        }else{
            authorities=new ArrayList<>();
        }
        ManagerPrincipal principal = new ManagerPrincipal(claims.getSubject(),"",authorities);
        principal.setSuperAdmin((boolean)claims.get(SUPER_ADMIN));
        principal.setFirstName((String)claims.get(FIRST_NAME));
        principal.setLastName((String)claims.get(LAST_NAME));
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }




    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }



}
