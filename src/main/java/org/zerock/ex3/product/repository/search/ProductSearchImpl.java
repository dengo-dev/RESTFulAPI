package org.zerock.ex3.product.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.ex3.product.dto.ProductListDTO;
import org.zerock.ex3.product.entity.ProductEntity;
import org.zerock.ex3.product.entity.QProductEntity;
import org.zerock.ex3.product.entity.QProductImage;

import java.util.List;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
  
  public ProductSearchImpl() {
    super(ProductEntity.class);
  }
  
  @Override
  
  public Page<ProductListDTO> list(Pageable pageable) {
    
    QProductEntity productEntity = QProductEntity.productEntity;
    QProductImage productImage = QProductImage.productImage;
    
    JPQLQuery<ProductEntity> query = from(productEntity);
    query.leftJoin(productEntity.images, productImage);
    
    //where productImage.idx=0
    query.where(productImage.idx.eq(0));
    
    //Long pno,String pname, int price, String writer, String productImage
    JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(Projections.bean(ProductListDTO.class,
        productEntity.pno,
        productEntity.pname,
        productEntity.price,
        productEntity.writer,
        productImage.fileName.as("productImage")));
    
    this.getQuerydsl().applyPagination(pageable, query);
    
    List<ProductListDTO> dtoList = dtojpqlQuery.fetch();
    long count= dtojpqlQuery.fetchCount();
    return new PageImpl<>(dtoList, pageable, count);
  }
}
