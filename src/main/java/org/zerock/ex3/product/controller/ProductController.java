package org.zerock.ex3.product.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.ex3.product.dto.PageRequestDTO;
import org.zerock.ex3.product.dto.ProductListDTO;
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
}
