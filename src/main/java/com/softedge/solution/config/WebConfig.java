package com.softedge.solution.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


public class WebConfig  {

    /*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }*

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*,/**")
                .allowedOrigins("http://40.117.59.221:5000")
                .allowedMethods("PUT", "DELETE", "OPTIONS","POST","GET")
              *//*  .allowedHeaders("Content-Type", "Depth", "Authorization","User-Agent","token","Bearer","cache-control", "X-RateLimit-Limit", "X-RateLimit-Remaining", "X-RateLimit-Reset","X-File-Size", "X-Requested-With", "If-Modified-Since", "X-File-Name", "Cache-Control")*//*
               .allowCredentials(true).maxAge(36000);
    }*/
}
