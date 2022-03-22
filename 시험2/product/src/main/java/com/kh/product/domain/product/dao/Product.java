package com.kh.product.domain.product.dao;

import lombok.Data;

@Data
public class Product {

  private Long productId;                 //  PRODUCT_ID	NUMBER(5,0)
  private String name;              //  NAME	VARCHAR2(30 BYTE)
  private Long quantity;               //  QUANTITY	NUMBER(5,0)
  private Long price;               //  PRICE	NUMBER(10,0)

}
