package org.zerock.ex3.config;


import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.ex3.member.security.filter.JWTCheckFilter;

@Configuration
@Log4j2
@EnableMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

  private JWTCheckFilter jwtCheckFilter;

  @Autowired
  private void setJwtCheckFilter(JWTCheckFilter jwtCheckFilter) {
    this.jwtCheckFilter = jwtCheckFilter;
  }

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

    //jwtCheckFilter를 UsernamePasswordAuthenticationFilter 앞에 두기
    httpSecurity.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
