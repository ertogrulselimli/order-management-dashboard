package com.ertogrul.omsb2b.web.security.config;


import com.ertogrul.omsb2b.web.security.jwt.JwtTokenFilterConfigurer;
import com.ertogrul.omsb2b.web.security.jwt.JwtTokenProvider;
import com.ertogrul.omsb2b.web.security.jwt.ManagerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;



@EnableWebSecurity
@Profile({"dev","test","mysql"})
@Import(SecurityProblemSupport.class)
@Configuration
public class SecurityConfigDev  extends WebSecurityConfigurerAdapter{

        @Autowired
        ManagerDetailService userDetailService;

        @Autowired
        private RestAuthEntryPoint restAuthEntryPoint;

        @Autowired
        CorsFilter corsFilter;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        JwtTokenProvider tokenProvider;

        @Autowired
        SecurityProblemSupport problemSupport;

        @Profile({"dev","test","mysql"})
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
        }


        private JwtTokenFilterConfigurer securityConfigurerAdapter() {
            return new JwtTokenFilterConfigurer(tokenProvider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.headers().frameOptions().sameOrigin();
            http.csrf().disable().
                    addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class).
                    exceptionHandling().
//                    authenticationEntryPoint(problemSupport).
//                    accessDeniedHandler(problemSupport).and().
                    and().authorizeRequests().
                    antMatchers(HttpMethod.OPTIONS, "/admin/api/**").
                    permitAll().
                    antMatchers("/swagger-ui/**",
                            "/swagger-resources/**",
                            "/v2/api-docs",
                            "/v3/api-docs").permitAll().
                    antMatchers("/admin/api/authenticate",
                            "/h2-console/**",  //dont ever use websecurity to ignore endpoints this confuses 403 and 404 errors
                            "/favicon.ico").
                    permitAll().
                    and().
                    antMatcher("/admin/**").
                    authorizeRequests()
                    .anyRequest().
                    authenticated().
                    and().
                    sessionManagement().
                    sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().
                    apply(securityConfigurerAdapter());
        }

    }

