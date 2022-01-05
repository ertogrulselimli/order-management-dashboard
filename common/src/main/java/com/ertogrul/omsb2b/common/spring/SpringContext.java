package com.ertogrul.omsb2b.common.spring;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/*

This is for used to call spring managed beans from non spring context

 */

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;


    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        //store ApplicationContext reference to access required beans later on
        SpringContext.context=context;
    }
}
