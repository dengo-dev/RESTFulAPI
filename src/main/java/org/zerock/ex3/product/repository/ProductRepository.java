package org.zerock.ex3.product.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.ex3.product.dto.ProductDTO;
import org.zerock.ex3.product.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  
  //관련된 엔티티나 값 객체등의 로딩을 조절하기위해서 사용함.
  //attributePaths를 이용해서 같이 로딩하려는 속성을 지정하고
  //EntityGraphType.FETCH를 지정하면 원하는 속성을 같이 로딩할 수 있다
  @EntityGraph(attributePaths = {"images"}, type = EntityGraph.EntityGraphType.FETCH)
  @Query("select p from ProductEntity  p where p.pno = :pno")
  Optional<ProductEntity> getProduct(@Param("pno") Long pno);
  
  //JPA에서 페치조인은 연관된 엔티티들을 한 번에 조회할 때 사용
  @Query("select p from ProductEntity  p join fetch p.images pi where p.pno= :pno")
  Optional<ProductDTO> getProductDTO(@Param("pno") Long pno);
}
