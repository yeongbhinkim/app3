package com.kh.comment.web;

import com.kh.comment.domain.Comment;
import com.kh.comment.domain.CommentDao;
import com.kh.comment.domain.CommentDaoImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequestMapping("/api/comments")
public class ApiCommentController {

  private final CommentDao commentDao = CommentDaoImpl.getInstance();

  //등록
  @PostMapping
  public ApiResult<Object> save(@RequestBody Comment comment){
    Comment savedComment = commentDao.save(comment);
    return new ApiResult<>("00","success",savedComment);
  }
  //목록
  @GetMapping
  public ApiResult<Object> findAll(){
    List<Comment> list = commentDao.findAll();
    return new ApiResult<>("00","success",list);
  }

  //조회
  @GetMapping("/{id}")
  public ApiResult<Object> findById(@PathVariable Long id){
    return new ApiResult<>("00","success",commentDao.findById(id));
  }

  //삭제
  @DeleteMapping("/{id}")
  public ApiResult<Object> delete(@PathVariable Long id){
    return new ApiResult<>("00","success",commentDao.delete(id));
  }
  //수정
  @PatchMapping("/{id}")
  public ApiResult<Object> update(@PathVariable Long id, @RequestBody Comment comment){
    return new ApiResult<>("00","success",commentDao.update(id,comment));
  }

  //전체삭제
  @DeleteMapping
  public void clearStore(){
    commentDao.clearStore();
  }
}





