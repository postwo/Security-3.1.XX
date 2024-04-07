package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ScurityConfig {

    //해시(단방향) = 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //회원가입 부터 보기

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        http
                .authorizeHttpRequests((auth)-> auth //시작은 이렇게
                        .requestMatchers("/","/login","/loginProc","join","joinProc").permitAll() //이경로는 모든사용자가 로그인하지 않아도 접근할수 있다는거다
                        .requestMatchers("/admin").hasRole("ADMIN") ///admin이경로는 admin계정만 접근가능하다
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER") // 이거는 admin또는 user면 접근 가능하다는거다
                        .anyRequest().authenticated() //위에서 처리하지 못한 나머지 경로르 처리ㅐ준다   //authenticated()로그인한 사용자만 접근 가능
                );
        
        //시큐리티 폼 로그인 
        http
                .formLogin((auth)->auth.loginPage("/login") //admin으로 로그인 안하고 접속하면 login페이지로 이동시킨다
                        .loginProcessingUrl("/loginProc") //loginProcessingUrl 이거는 html form action 주소
                        .permitAll() // 이경로로 아무나 접속 가능
                );

        //auth.disable() 테스트 할때는 이렇게 막아둔다
        http
                .csrf((auth)->auth.disable());


        return http.build(); //HttpSecurity http를 받아서 빌드 해준다
    }
}
