package com.kh.product.domain.product.dao;

import java.util.List;

public interface ProductDAO {

  //등록
  Product create(Product product);

  //상세
  Product selectOne(Long productId);

  //수정
  void update(Long productId, Product product);

  //삭제
  Product delete(Long productId);

  //전체조회
  List<Product> selectAll();
}
