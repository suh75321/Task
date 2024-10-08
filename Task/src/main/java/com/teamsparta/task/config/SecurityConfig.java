package com.teamsparta.task.config;

import com.teamsparta.task.exception.CustomAccessDeniedHandler;
import com.teamsparta.task.exception.CustomAuthenticationEntryPoint;
import com.teamsparta.task.jwt.JwtAuthenticationFilter;
import com.teamsparta.task.jwt.JwtAuthorizationFilter;
import com.teamsparta.task.jwt.JwtUtil;
import com.teamsparta.task.security.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)// 최신버전으로 변경
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;  // 401 처리
    private final CustomAccessDeniedHandler customAccessDeniedHandler;            // 403 처리

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthenticationConfiguration authenticationConfiguration,CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {//401, 403
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;//401
        this.customAccessDeniedHandler = customAccessDeniedHandler;//403
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/api/users/**").permitAll() // '/api/users/'로 시작하는 요청 모두 접근 허가
                        .requestMatchers("/api/todos/**").permitAll() // '/api/todos/'로 시작하는 요청 모두 접근 허가
                        .requestMatchers("/api/comments/**").permitAll() // '/api/comments/'로 시작하는 요청 모두 접근 허가
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger 관련 경로 허용.
                        // 안하면 스웨거도 로그인 해야함
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 401과 403 처리
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 401 처리
                        .accessDeniedHandler(customAccessDeniedHandler) // 403 처리
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
