package com.recommend.shyun.config;

import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;

@RequiredArgsConstructor
@Configuration
public class HandlebarsConfig {


    @Bean
    public ViewResolver handlerbarsViewResolver() {
        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
        viewResolver.registerHelpers(ConditionalHelpers.class); // 내장 헬퍼 등록
        viewResolver.registerHelpers(StringHelpers.class);      // 내장 헬퍼 등록

        viewResolver.setPrefix("classpath:/templates/");
        viewResolver.setSuffix(".hbs");
        viewResolver.setCache(false);

        return viewResolver;
    }
}

