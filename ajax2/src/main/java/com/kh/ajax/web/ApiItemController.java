package com.kh.ajax.web;

import com.kh.ajax.domain.Item;
import com.kh.ajax.domain.ItemDao;
import com.kh.ajax.domain.ItemDaoImpl;
import com.kh.ajax.web.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/items")
public class ApiItemController {

  private final ItemDao itemDAO = ItemDaoImpl.getInstance();

  //상품 등록
  @ResponseBody
  @PostMapping
  public ApiResult<Item> save(@RequestBody Item item) {
    Item savedItem = itemDAO.save(item);

    ApiResult<Item> result = new ApiResult<>("00", "success", savedItem);

    return result;
  }

  //상품 목록
  @ResponseBody
  @GetMapping
  public ApiResult<List<Item>> findAll() {
    List<Item> list = itemDAO.findAll();

    ApiResult<List<Item>> result = null;
    if (list.size() > 0) {
      result = new ApiResult<>("00", "success", list);
    } else {
      result = new ApiResult<>("99", "fail", null);
    }

    return result;
  }

  //상품 조회
  @ResponseBody
  @GetMapping("/{id}")
  public ApiResult<Item> findById(@PathVariable Long id) {
    Item findedItem = itemDAO.findById(id);

    ApiResult<Item> result = null;
    if (findedItem != null) {
      result = new ApiResult<Item>("00", "success", findedItem);
    } else {
      result = new ApiResult<Item>("02", "success", null);
    }

    return result;

  }

  //상품 삭제
  @ResponseBody
  @DeleteMapping("/{id}")
  public ApiResult<String> delete(@PathVariable Long id) {
    Item deletedItem = itemDAO.delete(id);

    ApiResult<String> result = null;
    if (deletedItem != null) {
      result = new ApiResult<>("00", "success", deletedItem.getName()+"가 삭제되었습니다.");
    } else {
      result = new ApiResult<>("99", "fail", "삭제할 상품이 없습니다.");
    }
    return result;
  }
  //상품 수정
  @ResponseBody
  @PatchMapping("/{id}")
  public ApiResult<Object> update(@PathVariable Long id, @RequestBody Item item) {
    Item updateBeforeItem = itemDAO.update(id, item);

    ApiResult<Object> result = null;
    if (updateBeforeItem != null) {
      result = new ApiResult<>("00", "success", itemDAO.findById(item.getId()));
    } else {
      result = new ApiResult<>("99", "fail", "상품 아이디가 없습니다.");
    }

    return result;
  }

  @PostConstruct //빈 생성자 생성 후 후처리
  public void init() {
    Item item = new Item();
    item.setName("키보드1");
    item.setBrand("LG");
    item.setDesc("LG키보드 - 1");
    item.setPrice(100000L);
    itemDAO.save(item); // id=1

    item = new Item();
    item.setName("키보드2");
    item.setBrand("LG");
    item.setDesc("LG키보드 - 2");
    item.setPrice(150000L);
    itemDAO.save(item); // id=2
  }
}
