package org.zerock.ex3.review.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex3.product.entity.ProductEntity;
import org.zerock.ex3.review.entity.ReviewEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ReviewRepositoryTests {

  @Autowired
  private ReviewRepository reviewRepository;

  @Test
  public void testInsert() {
    Long pno = 51L;

    ProductEntity productEntity = ProductEntity.builder().pno(pno).build();

    ReviewEntity reviewEntity = ReviewEntity.builder()
            .reviewText("리뷰 내용....")
            .score(5)
            .reviewer("reviewer1")
            .productEntity(productEntity)
            .build();

    reviewRepository.save(reviewEntity);
  }

  @Transactional
  @Test
  public void testRead() {
    Long rno = 1L;  //DB에 있는 리뷰 번호

    reviewRepository.findById(rno).ifPresent(reviewEntity -> {
      System.out.println(reviewEntity);
      System.out.println(reviewEntity.getProductEntity());

    });

  }

  @Test
  public void testGetWithProduct() {
    Long rno = 1L;

    //fetch join
    reviewRepository.getWithProduct(rno).ifPresent(reviewEntity -> {
      System.out.println(reviewEntity);
      System.out.println(reviewEntity.getProductEntity());

    });
  }
}
