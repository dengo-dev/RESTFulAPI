package org.zerock.ex3.cart.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex3.cart.entity.CartEntity;
import org.zerock.ex3.cart.entity.CartItemEntity;
import org.zerock.ex3.product.entity.ProductEntity;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CartRepositoryTests {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Transactional
  @Test
  @Commit
  public void testInsertCart() {
    String mid = "user22";
    Long pno = 50L;
    int qty = 5;

    Optional<CartEntity> cartResult = cartRepository.findByHolder(mid);
    CartEntity cartEntity = cartResult.orElseGet(() -> {
      CartEntity cart = CartEntity.builder().holder(mid).build();
      return cartRepository.save(cart);
    });
    ProductEntity productEntity = ProductEntity.builder().pno(pno).build();

    CartItemEntity cartItemEntity = CartItemEntity.builder()
            .cart(cartEntity)
            .product(productEntity)
            .quantity(qty)
            .build();

    cartItemRepository.save(cartItemEntity);
  }
}
