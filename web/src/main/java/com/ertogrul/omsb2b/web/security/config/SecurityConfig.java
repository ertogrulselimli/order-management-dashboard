package com.ertogrul.omsb2b.web.security.config;

import com.ertogrul.omsb2b.web.security.jwt.JwtTokenFilterConfigurer;
import com.ertogrul.omsb2b.web.security.jwt.JwtTokenProvider;
import com.ertogrul.omsb2b.web.security.jwt.ManagerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Profile("prod")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


        @Autowired
        ManagerDetailService userDetailService;

        @Autowired
        private RestAuthEntryPoint restAuthEntryPoint;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        JwtTokenProvider tokenProvider;

        @Profile("prod")
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }



        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            // configure AuthenticationManager so that it knows from where to load
            // user for matching credentials
            // Use BCryptPasswordEncoder
            auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
        }

        private JwtTokenFilterConfigurer securityConfigurerAdapter() {
            return new JwtTokenFilterConfigurer(tokenProvider);
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            //In prod profile we dont neew a swagger,h2-console and other endpoints
            http.csrf().disable().
                    authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/admin/api/**").permitAll().
                    antMatchers("/admin/api/authenticate").
                    permitAll().
                    antMatchers("/admin/**").authenticated().
                    and().
                    exceptionHandling().
                    accessDeniedHandler(restAuthEntryPoint).
                    authenticationEntryPoint(restAuthEntryPoint).
                    and().
                    sessionManagement().
                    sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().
                    apply(securityConfigurerAdapter());
        }
    }
