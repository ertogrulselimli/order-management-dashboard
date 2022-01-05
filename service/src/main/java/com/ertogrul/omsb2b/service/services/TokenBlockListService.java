package com.ertogrul.omsb2b.service.services;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenBlockListService {

    private static final Cache managerJwtTokenCache = CacheManager.getInstance().getCache("managerJwtTokenCache");

    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        log.info("Evicting expired tokens");
        managerJwtTokenCache.evictExpiredElements();
        managerJwtTokenCache.flush();
    }

    public void store(String token) {
        managerJwtTokenCache.put(new Element(token, token));
    }


    public boolean contains(String token) {
        return managerJwtTokenCache.get(token) != null;
    }

    public Authentication retrieve(String token) {
        return (Authentication) managerJwtTokenCache.get(token).getObjectValue();
    }

}

