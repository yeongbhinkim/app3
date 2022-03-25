package com.kh.product.web.form.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
public class DetailForm {
  private Long productId;                 //  PRODUCT_ID	NUMBER(5,0)
  @NotBlank
  private String name;              //  NAME	VARCHAR2(30 BYTE)
  @NotBlank
  private Long quantity;               //  QUANTITY	NUMBER(5,0)
  @NotBlank
  private Long price;               //  PRICE	NUMBER(10,0)
}
