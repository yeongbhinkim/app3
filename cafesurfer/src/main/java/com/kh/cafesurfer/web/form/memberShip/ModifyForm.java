package com.kh.cafesurfer.web.form.memberShip;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ModifyForm {

  @NotBlank
  @Email
  @Size(min = 4, max = 50)
  private String memberEmail;     //  MEMBER_EMAIL	VARCHAR2(40 BYTE)

  @NotBlank
  @Size(min = 4, max = 12)
  private String memberPasswd;    //  MEMBER_PASSWD	VARCHAR2(20 BYTE)

  @NotBlank
  @Size(min = 4, max = 30)
  private String memberName;      //  MEMBER_NAME	VARCHAR2(15 BYTE)


  private MemberGender memberGender;    // 남 여

  @NotBlank
  private Long memberAge;         //  MEMBER_AGE	NUMBER(3,0)

  @NotBlank
  private String memberTel;       //  MEMBER_TEL	VARCHAR2(15 BYTE)


}
