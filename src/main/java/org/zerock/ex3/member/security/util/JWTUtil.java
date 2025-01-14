package org.zerock.ex3.member.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {

  //JWT를 서명하고 검증하는 데 사용할 비밀 키
  private static String key = "1234567890123456789012345678901234567890";

  public String createToken(Map<String, Object> valueMap, int min) {

    //JWT의 서명에 사용되는 비밀 키를 저장하는 변수
    SecretKey key = null;

    try {
      //hmacShaKeyFor Method는 비밀 키를 HMAC SHA 알고리즘에 맞게 생성하는 유틸리티
      //key 문자열을 바이트 배열로 변환하고, 이를 사용하여 HMAC SHA 서명을 위한 키를 생성합니다.
      key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));

    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    //Jwts.builder()는 JWT 토큰을 생성하기 위한 빌더 객체
    return Jwts.builder().header()
            .add("typ", "JWT")
            .add("alg", "HS256")
            .and()
            .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
            .expiration((Date.from(ZonedDateTime.now()
                    .plusMinutes(min).toInstant()))).claims(valueMap)
            .signWith(key)
            .compact();  //최종적으로 JWT를 생성하고, 이를 compact(압축된) 형태로 반환
  }

  public Map<String, Object> validateToken(String token) {
    SecretKey key = null;

    try {
      key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    Claims claims = Jwts.parser().verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

    log.info("claims: " + claims);
    return claims;
  }
}
