package org.zerock.ex3.product.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.ex3.product.entity.ProductEntity;
import org.zerock.ex3.product.entity.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDTO {
  
  private Long pno;

  @NotEmpty
  private String  pname;

  @Min(0)
  private int price;
  
  private String content;

  @NotEmpty
  private String writer;
  
  private List<String> imageList;
  
  
  //productEntity를 파라미터로 상요하는 경우는 ProductRepository에서 직접 DTO를 반환하기 위해서
  public ProductDTO(ProductEntity productEntity) {
    this.pno = productEntity.getPno();
    this.pname = productEntity.getPname();
    this.price = productEntity.getPrice();
    this.content = productEntity.getContent();
    this.writer = productEntity.getWriter();
    this.imageList = productEntity.getImages().stream()
        .map(ProductImage::getFileName)
        .collect(Collectors.toList());
  }

  public ProductEntity toEntity() {
    ProductEntity productEntity = ProductEntity.builder()
            .pno(pno)
            .pname(pname)
            .price(price)
            .content(content)
            .writer(writer)
            .build();

    if (imageList == null || imageList.isEmpty()) {
      return productEntity;
    }
    imageList.forEach(productEntity::addImage);
    return productEntity;
  }
}
