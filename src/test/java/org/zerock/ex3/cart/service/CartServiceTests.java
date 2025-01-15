package org.zerock.ex3.cart.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex3.cart.dto.CartItemDTO;

import java.util.List;

@SpringBootTest
public class CartServiceTests {
  
  @Autowired
  private CartService cartService;
  
  @Test
  public void testGetCartList() {
    String mid = "user22";
    
    List<CartItemDTO> cartList = cartService.getAllItems(mid);
    
    cartList.forEach(cartItemDTO -> {
      System.out.println(cartItemDTO);
      
    });
  }
}
