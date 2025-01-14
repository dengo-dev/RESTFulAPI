package org.zerock.ex3.member.controller;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.ex3.member.dto.MemberDTO;
import org.zerock.ex3.member.security.util.JWTUtil;
import org.zerock.ex3.member.service.MemberService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/token")
@Log4j2
@RequiredArgsConstructor
public class TokenController {

  private final MemberService memberService;

  private final JWTUtil jwtUtil;

  @PostMapping("/make")
  public ResponseEntity<Map<String, String>> makeToken(@RequestBody MemberDTO memberDTO) {
    log.info("make token..........");

    //사용자의 mid,mpw를 memberDTO로 수집->해당 사용자가 있는지 확인하기위해서
    MemberDTO memberDTOResult = memberService.read(memberDTO.getMid(), memberDTO.getMpw());

    log.info(memberDTOResult);

    String mid = memberDTOResult.getMid();
    Map<String, Object> dataMap = memberDTOResult.getDataMap();
    String accessToken = jwtUtil.createToken(dataMap, 10);
    String refreshToken = jwtUtil.createToken(Map.of("mid", mid), 60 * 24 * 7);

    log.info("accessToken: " + accessToken);
    log.info("refreshToken: " + refreshToken);

    return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
  }

  @PostMapping("/refresh")
  public ResponseEntity<Map<String, String>> refreshToken(
          @RequestHeader("Authorization") String accessTokenStr,
          @RequestParam("refreshToken") String refreshToken,
          @RequestParam("mid") String mid
  ) {

    //적당한 값이 없다면 400에러 발생
    log.info("access token with Bearer..........." + accessTokenStr);

    if (accessTokenStr == null || !accessTokenStr.startsWith("Bearer ")) {
      return handleException("No Access Token", 400);
    }
    if (refreshToken == null) {
      return handleException("No Refresh Token", 400);
    }
    log.info("refresh token.........." + refreshToken);

    if (mid == null) {
      return handleException("No Mid", 400);
    }

    //Access Token이 만료되었는지 확인
    String accessToken = accessTokenStr.substring(7);

    try {
      jwtUtil.validateToken(accessToken);

      //아직 만료 기한이 남아있는 상황
      Map<String, String> data = makeData(mid, accessToken, refreshToken);

      log.info("Access Token is not expired..................");

      return ResponseEntity.ok(data);

    } catch (ExpiredJwtException expiredJwtException) {

      try {
        //Refresh가 필요한 상황
        Map<String, String> newTokenMap = makeNewToken(mid, refreshToken);
        return ResponseEntity.ok(newTokenMap);
      } catch (Exception e) {
        return handleException("REFRESH " + e.getMessage(), 400);
      }

    } catch (Exception e) {
      e.printStackTrace();
      return handleException(e.getMessage(), 400);
    }
  }

  private ResponseEntity<Map<String, String>> handleException(String msg, int status) {
    return ResponseEntity.status(status).body(Map.of("error", msg));
  }

  private Map<String, String> makeData(String mid, String accessToken, String refreshToken) {
    return Map.of("mid", mid, "accessToken", accessToken, "refreshToken", refreshToken);
  }

  private Map<String, String> makeNewToken(String mid, String refreshToken) {

      Map<String, Object> claims = jwtUtil.validateToken(refreshToken);
      if (!mid.equals(claims.get("mid").toString())) {
        throw new RuntimeException("Invalid Refresh Token Host");
      }

      //mid를 이용해서 사용자 정보를 다시 확인한 후에 새로운 토큰 생성
      MemberDTO memberDTO = memberService.getByMid(mid);

      Map<String, Object> newClaims = memberDTO.getDataMap();

      String newAccessToken = jwtUtil.createToken(newClaims, 10);

      String newRefreshToken = jwtUtil.createToken(Map.of("mid", mid), 60 * 24 * 7);

      return makeData(mid, newAccessToken, newRefreshToken);
  }
}
