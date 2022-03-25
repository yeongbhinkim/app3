package com.kh.product.domain.product.svc;

import com.kh.product.domain.product.dao.Product;
import com.kh.product.domain.product.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductSVCImpl implements ProductSVC{

  private final ProductDAO productDAO;

  //등록
  @Override
  public Product create(Product product) {
    return productDAO.create(product);
  }
  
  //조회
  @Override
  public Product selectOne(Long productId) {
    Product findedItem = productDAO.selectOne(productId);
    return findedItem;
  }

  //수정
  @Override
  public Product update(Long productId, Product product) {
    productDAO.update(productId,product);
    return product;
  }

  //삭제
  @Override
  public Product delete(Long productId) {
    return productDAO.delete(productId);
  }

  
  //전체조회
  @Override
  public List<Product> selectAll() {
    return productDAO.selectAll();
  }
}
