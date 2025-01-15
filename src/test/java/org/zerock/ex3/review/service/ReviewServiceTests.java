package org.zerock.ex3.review.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex3.review.dto.ReviewDTO;

@SpringBootTest
public class ReviewServiceTests {

  @Autowired
  private ReviewService reviewService;


  @Test
  public void testRegister() {
    Long pno = 100L;

    ReviewDTO reviewDTO = ReviewDTO.builder()
            .reviewText("리뷰 내용")
            .score(5)
            .reviewer("reviewer1")
            .pno(pno)
            .build();

    reviewService.register(reviewDTO);
  }
}
