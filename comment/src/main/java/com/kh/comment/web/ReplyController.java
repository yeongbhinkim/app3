package com.kh.comment.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/comment")
public class ReplyController {

  @GetMapping
  public String commentSvc(){

    return "commentSvc";
  }
}
