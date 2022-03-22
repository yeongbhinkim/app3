package com.kh.product.domain.product.svc;

import com.kh.product.domain.product.dao.Product;

import java.util.List;

public interface ProductSVC {

  //등록
  Product create(Product product);

  //상세
  Product selectOne(Long productId);

  //수정
  Product update(Long productId, Product product);

  //삭제
  Product delete(Long productId);

  //전체조회
  List<Product> selectAll();

}
