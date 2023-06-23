package com.hdekker.calendarhome.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean 
    protected SecurityFilterChain configure(HttpSecurity security) throws Exception {
    	
    	security.csrf().disable();
    	
        //Disable Spring's basic security settings as they are not relevant for this sample
        return security.authorizeHttpRequests()
        			.requestMatchers("/**")
        			.permitAll()
        			.and()
        			.build();
        
    }
}
