package com.ertogrul.omsb2b.web.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;


@Slf4j
@Configuration
public class WebConfiguration  implements WebMvcConfigurer {


    @Value("${alovtech.security.cors.allowed.origins}")
    private String allowedOrigins;

    @Value("${alovtech.security.cors.allowed.methods}")
    private String allowedMethods;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        configuration.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        if (configuration.getAllowedOrigins() != null && !configuration.getAllowedOrigins().isEmpty()) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/admin/api/**", configuration);
            source.registerCorsConfiguration("/admin/websocket",configuration);
        }
        return new CorsFilter(source);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/admin/api/**")
                .allowedOrigins(allowedOrigins.split(",")).
                allowedMethods(allowedMethods.split(",")).
                allowCredentials(true).allowedHeaders("*");
       // registry.addMapping("/admin/api/managerinfo").allowCredentials(true).allowedOrigins(allowedOrigins.split(","));
    }


}
