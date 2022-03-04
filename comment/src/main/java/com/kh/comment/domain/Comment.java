package com.kh.comment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Comment {
  private Long id;              //댓글 식별자
  private String email;         //댓글 작성자 아이디
  private String nickname;      //댓글 작성자 별칭
  private String content;       //댓글 내용
  private LocalDateTime cdate;  //작성일시
  private LocalDateTime udate;  //수정일시
}
