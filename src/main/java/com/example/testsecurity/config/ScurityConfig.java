package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
        //이걸 disable 을 안걸경우는
        //<input type="hidden" name="_csrf" value="{{_csrf.token}}"/>이렇게 넣어준다
//        http
//                .csrf((auth)->auth.disable());



        //동일한 아이디로 다중 로그인을 진행할 경우에 대한 설정 방법은 세션 통제를 통해
        /*sessionManagement() 메소드를 통한 설정을 진행한다.
        maximumSession(정수) : 하나의 아이디에 대한 다중 로그인 허용 개수
        maxSessionPreventsLogin(불린) : 다중 로그인 개수를 초과하였을 경우 처리 방법
            - true : 초과시 새로운 로그인 차단
            - false : 초과시 기존 세션 하나 삭제*/
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));


        /*세션 고정 공격을 보호하기 위한 로그인 성공시 세션 설정 방법은 sessionManagement() 메소드의 sessionFixation() 메소드를 통해서 설정할 수 있다.
        - sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
        - sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
        - sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경*/
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()); //주로 이방식으로 구현한다

        return http.build(); //HttpSecurity http를 받아서 빌드 해준다
    }

    //InMemory 방식 유저 저장
    /*토이 프로젝트를 진행하는 경우 또는 시큐리티 로그인 환경이 필요하지만
    소수의 회원 정보만 가지며 데이터베이스라는 자원을 투자하기
    힘든 경우는 회원가입 없는 InMemory 방식으로 유저를 저장하면 된다.
     이 경우 InMemoryUserDetailsManager 클래스를 통해 유저를
     등록하면 된다.*/

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

}
