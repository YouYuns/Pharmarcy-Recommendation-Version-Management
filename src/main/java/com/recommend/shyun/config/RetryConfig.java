package com.recommend.shyun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry
@Configuration
public class RetryConfig {

    
    //어노테이션 사용안할거면 아래같이 사용가능
//    @Bean
//    public RetryTemplate retryTemplate(){
//        return new RetryTemplate();
//    }


    
}
