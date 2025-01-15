package org.zerock.ex3.review.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ex3.review.dto.ReviewDTO;
import org.zerock.ex3.review.exception.ReviewExceptions;
import org.zerock.ex3.review.service.ReviewService;

import java.security.Principal;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping("")
  public ResponseEntity<ReviewDTO> register(@RequestBody @Validated ReviewDTO reviewDTO, Principal principal) {
    log.info("register: " + reviewDTO);

    if (!principal.getName().equals(reviewDTO.getReviewer())) {
      throw ReviewExceptions.REVIEWER_MISMATCH.get();
    }
    //ReviewTaskException은 RuntimeException계열이므로 별도의 예외처리가 필요하지 않다.
    return ResponseEntity.ok(reviewService.register(reviewDTO));
  }
}
