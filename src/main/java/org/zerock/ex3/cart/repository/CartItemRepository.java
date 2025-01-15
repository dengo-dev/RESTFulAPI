package org.zerock.ex3.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex3.cart.entity.CartItemEntity;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

}
