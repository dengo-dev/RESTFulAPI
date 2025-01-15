package org.zerock.ex3.product.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex3.product.dto.ProductDTO;
import org.zerock.ex3.product.entity.ProductEntity;
import org.zerock.ex3.product.exception.ProductExceptions;
import org.zerock.ex3.product.repository.ProductRepository;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductService {

  private final ProductRepository productRepository;

  public ProductDTO register(ProductDTO productDTO) {
    try {
      log.info("register..............");
      log.info(productDTO);

      ProductEntity productEntity = productDTO.toEntity();

      productRepository.save(productEntity);
      return new ProductDTO(productEntity);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw ProductExceptions.PRODUCT_NOT_REGISTERED.get();
    }
    
  }
}
