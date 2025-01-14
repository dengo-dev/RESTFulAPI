package org.zerock.ex3.config;


import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class CustomSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    log.info("filter chain...................");

    httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> {
      httpSecurityFormLoginConfigurer.disable();
    });

    httpSecurity.logout(config -> config.disable());
    httpSecurity.csrf(config -> {
      config.disable();
    });

    httpSecurity.sessionManagement(sessionManagementConfigurer -> {
      sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.NEVER); //세션을 절대로 생생하지 않음
    });



    return httpSecurity.build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
