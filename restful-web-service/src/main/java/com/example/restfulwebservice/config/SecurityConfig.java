package com.example.restfulwebservice.config;

import com.example.restfulwebservice.dao.UserDaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {
    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails newUser = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .authorities("read")
                .build();

        userDetailsManager.createUser(newUser);
        return userDetailsManager;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 사용하고자 하는 url에서 무인증으로 사용 가능한 부분
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        // 인증 무시하는 로직, webflux나 reactive 부분에서 사용되는 체이닝 코드
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/jpa/**"));
    }

    // SecurityFilterChain filterChain 추가하여 csft 문제 해결(리소스 변경 가져오는 create, update, delete 등 사용하기 위해서)
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception{
        // '::'은 메소드 레퍼러스
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
