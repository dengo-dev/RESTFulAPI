package org.zerock.ex3.product.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable //이 엔티티는 객체가 아니라 다른 엔티티의 속성값임
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ProductImage implements Comparable<ProductImage>{
  
  private int idx;
  
  private String fileName;
  
  @Override
  public int compareTo(ProductImage o) {
    return this.idx - o.idx;
  }
}
