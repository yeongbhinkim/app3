package com.kh.product.domain.product.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ProductDAOImplTest {

  @Autowired
  private ProductDAO productDAO;

  @Test
  @DisplayName("등록")
  void create() {
    Product product = new Product();
    product.setName("커피10");
    product.setQuantity(6L);
    product.setPrice(22300L);

    Product create = productDAO.create(product);
    Assertions.assertThat(create.getName()).isEqualTo("커피10");
    log.info("create={}", create);
  }

  @Test
  @DisplayName("조회")
  void selectOne() {
    Long productId = 4L;
    Product findByBbsItem = productDAO.selectOne(productId);
    Assertions.assertThat(findByBbsItem.getName()).isEqualTo("커피우유2");
  }

  @Test
  @DisplayName("수정")
  void update() {

    Long productId = 7L;
    //수정전
    Product beforeUpdatingItem = productDAO.selectOne(productId);
    beforeUpdatingItem.setName("수정후 제품");
    beforeUpdatingItem.setQuantity(15L);
    beforeUpdatingItem.setPrice(15000L);
    productDAO.update(productId, beforeUpdatingItem);

    //수정후
    Product afterUpdatingItem = productDAO.selectOne(productId);

    //수정전후 비교
    Assertions.assertThat(beforeUpdatingItem.getName())
        .isEqualTo(afterUpdatingItem.getName());
    Assertions.assertThat(beforeUpdatingItem.getQuantity())
        .isEqualTo(afterUpdatingItem.getQuantity());
    Assertions.assertThat(beforeUpdatingItem.getPrice())
        .isEqualTo(afterUpdatingItem.getPrice());

  }

  @Test
  @DisplayName("삭제")
  void delete() {
    Long productId = 8L;
    Product deletedBbsItemCount = productDAO.delete(productId);

    Assertions.assertThat(deletedBbsItemCount).isEqualTo(null);

    Product findedBbsItem = productDAO.selectOne(productId);
    Assertions.assertThat(findedBbsItem).isNull();
  }

  @Test
  @DisplayName("전제조회")
  void selectAll() {
    List<Product> list = productDAO.selectAll();

    Assertions.assertThat(list.get(0).getName()).isEqualTo("커피우유2");
    for (Product product : list) {
      log.info(product.toString());
    }
  }
}