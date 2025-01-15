package org.zerock.ex3.cart.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddCartItemDTO {
  
  private String holder;
  
  private Long pno;
  
  private int quantity;
}
