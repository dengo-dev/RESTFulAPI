package org.zerock.ex3.product.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zerock.ex3.product.dto.PageRequestDTO;
import org.zerock.ex3.product.dto.ProductDTO;
import org.zerock.ex3.product.dto.ProductListDTO;
import org.zerock.ex3.product.exception.ProductExceptions;
import org.zerock.ex3.product.service.ProductService;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

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

  @GetMapping("/{pno}")
  public ResponseEntity<ProductDTO> read(@PathVariable("pno") Long pno) {
    log.info("read............");
    log.info(pno);

    ProductDTO productDTO = productService.read(pno);

    return ResponseEntity.ok(productDTO);
  }

  @DeleteMapping("/{pno}")
  public ResponseEntity<Map<String, String>> remove(@PathVariable("pno") Long pno, Authentication authentication) {

    log.info("remove.............");
    log.info(pno);
    log.info(authentication.getName());
    log.info(authentication.getAuthorities());

    ProductDTO productDTO = productService.read(pno);

    if (!productDTO.getWriter().equals(authentication.getName())) {
      //현재 사용자의 권한
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

      //ADMIN권한이 없는 경우 예외발생
      authorities.stream().filter(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
              .findAny().orElseThrow(ProductExceptions.PRODUCT_WRITER_ERROR::get);
    }
    productService.remove(pno);
    return ResponseEntity.ok(Map.of("result", "success"));
  }

}
