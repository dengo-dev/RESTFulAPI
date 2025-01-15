package org.zerock.ex3.review.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.AttributeAccessor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.zerock.ex3.product.entity.ProductEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_reviews", indexes = @Index(columnList = "product_pno")) //상품 번호를 빠르게 조회하기 위해
@Getter
@ToString(exclude = "productEntity")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class ReviewEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rno;

  private String reviewText;

  private String reviewer;

  private int score;

  @ManyToOne(fetch = FetchType.LAZY)//ProductEntity를 단 방향 참조
  @JoinColumn(name = "product_pno") //ProductEntity는 ReviewEntity의 존재를 모른다.
  private ProductEntity productEntity;

  @CreatedDate
  private LocalDateTime reviewDate;

  @LastModifiedDate
  private LocalDateTime modifiedDate;

  public void changeReviewText(String reviewText) {
    this.reviewText = reviewText;
  }

  public void changeScore(int score) {
    this.score = score;
  }
}
