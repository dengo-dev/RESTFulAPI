package org.zerock.ex3.cart.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.zerock.ex3.cart.dto.AddCartItemDTO;
import org.zerock.ex3.cart.dto.CartItemDTO;
import org.zerock.ex3.cart.entity.ModifyCartItemDTO;
import org.zerock.ex3.cart.exception.CartTaskException;
import org.zerock.ex3.cart.service.CartService;

import java.security.Principal;
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
  
  @GetMapping("/{cno}") //AccessToken에 있는 mid값을 이용해서 해당 장바구니의 번호가 자신의 장바구니인지 확인하고 장바구니에 들어있는 모든 아이템을 반환
  public ResponseEntity<List<CartItemDTO>> getCartItemList(@PathVariable("cno") Long cno, Principal principal) {
    
    log.info("get cart ................" + cno);
    
    String mid = principal.getName();
    
    cartService.checkItemHolder(mid, cno);
    
    List<CartItemDTO> cartList = cartService.getAllItems(mid);
    
    return ResponseEntity.ok(cartList);
  }
  
  @PutMapping("/modifyItem/{itemNo}")
  public ResponseEntity<List<CartItemDTO>> modifyItem(
      @PathVariable("itemNo") Long itemNo,
      @RequestBody ModifyCartItemDTO modifyCartItemDTO,
      Principal principal) {
    
    log.info("modify item..............." + modifyCartItemDTO);
    
    String mid = principal.getName();
    
    log.info("mid: " + mid);
    
    cartService.checkItemHolder(mid, itemNo);
    
    modifyCartItemDTO.setItemNo(itemNo);
    
    cartService.modifyItem(modifyCartItemDTO);
    
    List<CartItemDTO> cartList = cartService.getAllItems(mid);
    
    return ResponseEntity.ok(cartList);
  }
  
  
  @DeleteMapping("/removeItem/{itemNo}")
  public ResponseEntity<List<CartItemDTO>> removeItem(
      @PathVariable("itemNo") Long itemNo,
      Principal principal) {
    
    log.info("remove item..............." + itemNo);
    
    String mid = principal.getName();
    
    cartService.checkItemHolder(mid, itemNo);
    
    cartService.modifyItem(
        ModifyCartItemDTO.builder().itemNo(itemNo).quantity(0).build()
    );
    
    List<CartItemDTO> cartList = cartService.getAllItems(mid);
    
    return ResponseEntity.ok(cartList);
  }
}
