package com.board.project_sample.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final MemberAuthArgumentResolver memberAuthArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(memberAuthArgumentResolver);
    }

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //모든 경로에 대해
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                // GET, POST, PUT, PATCH, DELETE, OPTIONS 메서드를 허용
                .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}
