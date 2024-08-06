package com.teamsparta.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS를 허용
                .allowedOrigins("http://localhost:8080") // 허용할 출처
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS" ) // 허용할 HTTP 메서드
                .allowCredentials(true) // 쿠키 전송 허용
                .maxAge(3600); // preflight 요청의 유효 시간
    }
}
