package org.zerock.ex3.member.security.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.ex3.member.security.util.JWTUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;

  @Override //JWT CheckFilter가 동작하지 않아야 하는 경로 지정
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    if (request.getServletPath().startsWith("/api/v1/token")) {
      return true;
    }

    return false;
  }

  @Override //Access Token을 꺼내서 검증
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    log.info("JWTCheckFilter doFilter............");
    log.info("requestURI: " + request.getRequestURI());

    String headerStr = request.getHeader("Authorization");
    log.info("headerStr: " + headerStr);

    //Access Token이 없는 경우
    if (headerStr == null | !headerStr.startsWith("Bearer ")) {
      handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
      return;
    }
  }

  private void handleException(HttpServletResponse response, Exception e)throws IOException {
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    response.setContentType("application/json");
    response.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
  }

}
