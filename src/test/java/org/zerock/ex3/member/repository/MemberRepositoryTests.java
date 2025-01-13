package org.zerock.ex3.member.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberRepositoryTests {
  
  @Autowired
  private PasswordEncoder passwordEncoder;
}
