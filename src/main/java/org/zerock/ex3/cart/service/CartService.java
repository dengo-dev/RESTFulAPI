package org.zerock.ex3.cart.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex3.cart.dto.CartItemDTO;
import org.zerock.ex3.cart.entity.CartItemEntity;
import org.zerock.ex3.cart.repository.CartItemRepository;
import org.zerock.ex3.cart.repository.CartRepository;
import org.zerock.ex3.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CartService {
  
  private final CartItemRepository cartItemRepository;
  
  private final CartRepository cartRepository;
  
  private final ProductRepository productRepository;
  
  
  public List<CartItemDTO> getAllItems(String mid) {
    
    List<CartItemDTO> itemDTOList = new ArrayList<>();
    
    Optional<List<CartItemEntity>> result = cartItemRepository.getCartItemsOfHolder(mid);
    
    if (result.isEmpty()) {
      return itemDTOList;
    }
    
    List<CartItemEntity> cartItemEntityList = result.get();
    
    cartItemEntityList.forEach(cartItemEntity -> {
      itemDTOList.add(entityToDTO(cartItemEntity));
      
    });
    
    return itemDTOList;
  }
  
  private CartItemDTO entityToDTO(CartItemEntity cartItemEntity) {
    return CartItemDTO.builder()
        .itemNo(cartItemEntity.getItemNo())
        .pname(cartItemEntity.getProduct().getPname())
        .pno(cartItemEntity.getProduct().getPno())
        .price(cartItemEntity.getProduct().getPrice())
        .image(cartItemEntity.getProduct().getImages().first().getFileName())
        .quantity(cartItemEntity.getQuantity())
        .build();
  }
}
