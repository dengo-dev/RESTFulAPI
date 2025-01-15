package org.zerock.ex3.review.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex3.review.dto.ReviewDTO;
import org.zerock.ex3.review.entity.ReviewEntity;
import org.zerock.ex3.review.exception.ReviewExceptions;
import org.zerock.ex3.review.repository.ReviewRepository;

@SpringBootTest
public class ReviewServiceTests {

  @Autowired
  private ReviewService reviewService;
  @Autowired
  private ReviewRepository reviewRepository;


  @Test
  public void testRegister() {
    Long pno = 3L;

    ReviewDTO reviewDTO = ReviewDTO.builder()
            .reviewText("리뷰 내용")
            .score(5)
            .reviewer("reviewer1")
            .pno(pno)
            .build();

    reviewService.register(reviewDTO);
  }

  @Test
  public void testRead() {
    Long rno = 1L;

    ReviewDTO reviewDTO = reviewService.read(rno);

    System.out.println(reviewDTO);


  }
}
