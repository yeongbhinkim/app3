package com.kh.product.web;

import com.kh.product.domain.product.dao.Product;
import com.kh.product.domain.product.svc.ProductSVC;
import com.kh.product.web.api.ApiResult;
import com.kh.product.web.form.product.AddForm;
import com.kh.product.web.form.product.DetailForm;
import com.kh.product.web.form.product.EditForm;
import com.kh.product.web.form.product.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

  private final ProductSVC productSVC;

  // 등록화면 GET /notices/add
  @GetMapping("")
  public String addForm(@ModelAttribute AddForm addForm) {
    log.info("productController.addForm() 호출됨!");
    return "product/addForm";
  }

  // 등록처리
  @PostMapping("")
  public String add(
      @ModelAttribute AddForm addForm,
      RedirectAttributes redirectAttributes,
      Model model) {

    log.info("productController.add() 호출됨!");
    log.info("AddForm={}", addForm);

    Product product = new Product();
    product.setName(addForm.getName());
    product.setQuantity(addForm.getQuantity());
    product.setPrice(addForm.getPrice());

    Product createproduct = productSVC.create(product);
    redirectAttributes.addAttribute("productId",createproduct.getProductId());

    return "redirect:/api/product/{productId}/detail"; //http://서버:9080/notices/공지사항번호
  }

  // 상세화면
  @GetMapping("/{productId}/detail")
  public String detailForm(@PathVariable Long productId, Model model) {

    Product product = productSVC.selectOne(productId);

    DetailForm detailForm =new DetailForm();
    detailForm.setProductId(productId);
    detailForm.setName(product.getName());
    detailForm.setQuantity(product.getQuantity());
    detailForm.setPrice(product.getPrice());

    model.addAttribute("detailForm",detailForm);

    return "product/detailForm";
  }

  // 수정화면
  @GetMapping("/{productId}")
  public String editForm(@PathVariable Long productId,
                         Model model) {

    Product product = productSVC.selectOne(productId);

    EditForm editForm = new EditForm();
    editForm.setProductId(productId);
    editForm.setName(product.getName());
    editForm.setQuantity(product.getQuantity());
    editForm.setPrice(product.getPrice());

    model.addAttribute("editForm",editForm);

    return "product/editForm";
  }

  // 수정처리
  @PatchMapping("/{productId}")
  public String edit(
      @ModelAttribute EditForm editForm,
      @PathVariable Long productId,
      RedirectAttributes redirectAttributes
  ) {

    Product product = new Product();
    product.setProductId(productId);
    product.setName(editForm.getName());
    product.setQuantity(editForm.getQuantity());
    product.setPrice(editForm.getPrice());


    Product updateProduct = productSVC.update(productId, product);

    redirectAttributes.addAttribute("productId", updateProduct.getProductId());

    return "redirect:/api/product/{productId}/detail";
  }

  // 삭세처리
  @DeleteMapping("{productId}")
  public String del(@PathVariable Long productId) {

    productSVC.delete(productId);

    return "redirect:/api/product/all";
  }

  // 전체목록
  @GetMapping("/all")
  public String list(Model model) {

    List<Product> list = productSVC.selectAll();

    List<Item> products = new ArrayList<>();

    for (Product product : list) {
      Item item = new Item();
      item.setProductId(product.getProductId());
      item.setName(product.getName());
      item.setQuantity(product.getQuantity());
      item.setPrice(product.getPrice());

      products.add(item);
    }

    model.addAttribute("products", products);
    return "product/list";
  }
//  //상품등록
//  @ResponseBody
//  @PostMapping
//  public ApiResult<Product> save(@RequestBody Product product) {
//
//    Product productItem = productSVC.create(product);
//
//    ApiResult<Product> result = new ApiResult<>("00", "success", productItem);
//    return result;
//  }
//
//  //상품목록
//  @ResponseBody
//  @GetMapping
//  public ApiResult<List<Product>> selectAll() {
//
//    List<Product> list = productSVC.selectAll();
//    ApiResult<List<Product>> result = null;
//    if (list.size() > 0) {
//      result = new ApiResult<>("00", "success", list);
//    } else {
//      result = new ApiResult<>("02", "success", null);
//    }
//    return result;
//  }
//
//  //상품조회
//  @ResponseBody
//  @GetMapping("/{productId}")
//  public ApiResult<Product> selectOne(@PathVariable Long productId) {
//    Product findedItem = productSVC.selectOne(productId);
//    ApiResult<Product> result = null;
//    if (findedItem != null) {
//      result = new ApiResult<>("00", "success", findedItem);
//    } else {
//      result = new ApiResult<>("99", "fail", null);
//    }
//    return result;
//  }
//
//  //상품삭제
//  @ResponseBody
//  @DeleteMapping("/{productId}")
//  public ApiResult<String> delete(@PathVariable Long productId) {
//    Product deletedItem = productSVC.delete(productId);
//
//    ApiResult<String> result = null;
//    if (deletedItem != null) {
//      result = new ApiResult<>("99", "fail", "삭제할 상품이 없습니다.");
//    } else {
//      result = new ApiResult<>("00", "success", "삭제되었습니다.");
//    }
//    return result;
//  }
//
//  //상품수정
//  @ResponseBody
//  @PatchMapping("/{productId}")
//  public ApiResult<Object> update(@PathVariable Long productId,
//                                  @RequestBody Product product
//  ) {
//    Product updateBeforeItem = productSVC.update(productId, product);
//
//    ApiResult<Object> result = null;
//    if (updateBeforeItem != null) {
//      result = new ApiResult<>("00", "success", productSVC.update(productId,product));
//    } else {
//      result = new ApiResult<>("99", "fail", "상품 아이디가 없습니다.");
//    }
//    return result;
//  }


}
