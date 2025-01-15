package org.zerock.ex3.product.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zerock.ex3.product.dto.PageRequestDTO;
import org.zerock.ex3.product.dto.ProductDTO;
import org.zerock.ex3.product.dto.ProductListDTO;
import org.zerock.ex3.product.exception.ProductExceptions;
import org.zerock.ex3.product.service.ProductService;

import java.security.Principal;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/list")
  public ResponseEntity<Page<ProductListDTO>> list(@Validated PageRequestDTO pageRequestDTO, Principal principal) {
    log.info(pageRequestDTO);
    log.info(principal.getName());

    return ResponseEntity.ok(productService.getList(pageRequestDTO));
  }

  @PostMapping("")
  public ResponseEntity<ProductDTO> register(
          @RequestBody @Validated ProductDTO productDTO, Principal principal
  ) {
    log.info("register...........");
    log.info(productDTO);

    if (productDTO.getImageList() == null || productDTO.getImageList().isEmpty()) {
      throw ProductExceptions.PRODUCT_NO_IMAGE.get();
    }

    if (!principal.getName().equals(productDTO.getWriter())) {
      throw ProductExceptions.PRODUCT_WRITER_ERROR.get();
    }
    return ResponseEntity.ok(productService.register(productDTO));
  }

}
