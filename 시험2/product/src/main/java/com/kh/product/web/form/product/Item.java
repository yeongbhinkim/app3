package com.kh.product.web.form.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
  private Long productId;                 //  PRODUCT_ID	NUMBER(5,0)
  private String name;              //  NAME	VARCHAR2(30 BYTE)
  private Long quantity;               //  QUANTITY	NUMBER(5,0)
  private Long price;               //  PRICE	NUMBER(10,0)
}
