package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ScurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        http
                .authorizeHttpRequests((auth)-> auth //시작은 이렇게
                        .requestMatchers("/","/login").permitAll() //이경로는 모든사용자가 로그인하지 않아도 접근할수 있다는거다
                        .requestMatchers("/admin").hasRole("ADMIN") ///admin이경로는 admin계정만 접근가능하다
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER") // 이거는 admin또는 user면 접근 가능하다는거다
                        .anyRequest().authenticated() //위에서 처리하지 못한 나머지 경로르 처리ㅐ준다   //authenticated()로그인한 사용자만 접근 가능
                );

        

        return http.build(); //HttpSecurity http를 받아서 빌드 해준다
    }
}
