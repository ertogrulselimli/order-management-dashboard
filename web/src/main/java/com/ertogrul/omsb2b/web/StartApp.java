package com.ertogrul.omsb2b.web;


import com.ertogrul.omsb2b.persistence.repositories.CustomRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@EnableJpaRepositories(basePackages = {"com.alovtech.gwaters.persistence.repositories"},repositoryBaseClass = CustomRepositoryImpl.class)
@EntityScan(basePackages = {"com.alovtech.gwaters.persistence.entities"})
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@ComponentScan(basePackages = "com.alovtech.gwaters")
@EnableScheduling
@SpringBootApplication
public class StartApp  extends SpringBootServletInitializer  {


    public static void main(String[] args){
        SpringApplication.run(StartApp.class,args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StartApp.class);
    }

}
