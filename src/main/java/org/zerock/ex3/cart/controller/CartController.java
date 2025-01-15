package org.zerock.ex3.cart.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ex3.cart.dto.AddCartItemDTO;
import org.zerock.ex3.cart.dto.CartItemDTO;
import org.zerock.ex3.cart.exception.CartTaskException;
import org.zerock.ex3.cart.service.CartService;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
  
  private final CartService cartService;
  
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/addItem")
  public ResponseEntity<List<CartItemDTO>> addItem(@RequestBody AddCartItemDTO addCartItemDTO, Authentication authentication) {
    
    log.info("add item...........");
    log.info(authentication.getName());
    
    if (!addCartItemDTO.getHolder().equals(authentication.getName())) {
      throw CartTaskException.Items.NOT_CARTITEM_OWNER.value();
    }
    
    cartService.registerItem(addCartItemDTO);
    
    List<CartItemDTO> cartItemList = cartService.getAllItems(addCartItemDTO.getHolder());
    
    return ResponseEntity.ok(cartItemList);
  }
}
