package org.zerock.ex3.member.security.auth;


import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RequiredArgsConstructor
public class CustomUserPrincipal implements Principal { //CustomUserPrincipal은 사용자 정보를 저장

  private final String mid;

  @Override
  public String getName() {
    return mid;
  }
}
