package com.kh.product.web;

import com.kh.product.domain.product.dao.Product;
import com.kh.product.domain.product.svc.ProductSVC;
import com.kh.product.web.api.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

  private final ProductSVC productSVC;


  //상품등록
  @ResponseBody
  @PostMapping
  public ApiResult<Product> save(@RequestBody Product product) {

    Product productItem = productSVC.create(product);

    ApiResult<Product> result = new ApiResult<>("00", "success", productItem);
    return result;
  }

  //상품목록
  @ResponseBody
  @GetMapping
  public ApiResult<List<Product>> selectAll() {

    List<Product> list = productSVC.selectAll();
    ApiResult<List<Product>> result = null;
    if (list.size() > 0) {
      result = new ApiResult<>("00", "success", list);
    } else {
      result = new ApiResult<>("02", "success", null);
    }
    return result;
  }

  //상품조회
  @ResponseBody
  @GetMapping("/{productId}")
  public ApiResult<Product> selectOne(@PathVariable Long productId) {
    Product findedItem = productSVC.selectOne(productId);
    ApiResult<Product> result = null;
    if (findedItem != null) {
      result = new ApiResult<>("00", "success", findedItem);
    } else {
      result = new ApiResult<>("99", "fail", null);
    }
    return result;
  }

  //상품삭제
  @ResponseBody
  @DeleteMapping("/{productId}")
  public ApiResult<String> delete(@PathVariable Long productId) {
    Product deletedItem = productSVC.delete(productId);

    ApiResult<String> result = null;
    if (deletedItem != null) {
      result = new ApiResult<>("99", "fail", "삭제할 상품이 없습니다.");
    } else {
      result = new ApiResult<>("00", "success", "삭제되었습니다.");
    }
    return result;
  }

  //상품수정
  @ResponseBody
  @PatchMapping("/{productId}")
  public ApiResult<Object> update(@PathVariable Long productId,
                                  @RequestBody Product product
  ) {
    Product updateBeforeItem = productSVC.update(productId, product);

    ApiResult<Object> result = null;
    if (updateBeforeItem != null) {
      result = new ApiResult<>("00", "success", productSVC.update(productId,product));
    } else {
      result = new ApiResult<>("99", "fail", "상품 아이디가 없습니다.");
    }
    return result;
  }


}
