package org.zerock.ex3.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ex3.cart.entity.CartEntity;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

  //소유주로 CartEntity를 검색
  Optional<CartEntity> findByHolder(String holder);
}
