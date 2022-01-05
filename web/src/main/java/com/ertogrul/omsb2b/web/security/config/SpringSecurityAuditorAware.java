package com.ertogrul.omsb2b.web.security.config;


import com.ertogrul.omsb2b.common.constants.SecurityConstants;
import com.ertogrul.omsb2b.common.utils.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of AuditorAware based on Spring Security.
 */

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(SecurityConstants.SYSTEM_ACCOUNT));
    }




}
