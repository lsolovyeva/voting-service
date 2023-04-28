/*package org.example.exception;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
public class SecurityBeanConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> filterRegistrationBean(CustomFilter customFilter) {
        FilterRegistrationBean<CustomFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(customFilter);
        filterRegistrationBean
                //.setOrder(Integer.MAX_VALUE);
                .setEnabled(false);
        return filterRegistrationBean;
    }

    /*@Bean
    public FilterRegistrationBean securityFilterChain(CustomFilter securityFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(0);
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }*/

    /*@Bean
    public FilterRegistrationBean<AccessDeniedExceptionFilter> filterRegistrationBean(AccessDeniedExceptionFilter customFilter) {
        FilterRegistrationBean<AccessDeniedExceptionFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(customFilter);
        filterRegistrationBean.setOrder(1);
        //.setEnabled(false);
        return filterRegistrationBean;
    }
}
*/