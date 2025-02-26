package org.zerock.club.config;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.zerock.club.security.handler.ClubLoginSuccessHandler;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1111"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth-> {
//            auth.requestMatchers("/sample/all").permitAll() // 로그인없이 접속 가능
//                    .requestMatchers("/sample/member").hasRole("USER") // 해당롤을 가지고 있으면 접속 가능
//                    .anyRequest().permitAll(); // 위에설정외 나머지 URL 로그인 없이 접속 가능
//        });
        http.formLogin(formLogin -> {
//            formLogin.loginPage("/member/login");
        });
        // 토큰비활성화
        http.csrf(csrf->{
            csrf.disable();
        });
        // 로그아웃(반드시 post방식으로+토큰 가능)
        http.logout(logout -> {

        });
        http.oauth2Login(login -> {
            login.successHandler(clubLoginSuccessHandler());
        });
        // Remember-me
        http.rememberMe(m->m.tokenValiditySeconds(60*60*24*7));
        return http.build();
    }
    @Bean
    public ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }
}
