package com.kh.comment.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDaoImpl implements CommentDao{

  private static Map<Long, Comment> store = new HashMap<>();
  private static final CommentDaoImpl commentDaoImpl = new CommentDaoImpl();
  private static long seq = 0L;

  private CommentDaoImpl(){};

  public static CommentDaoImpl getInstance(){
    return commentDaoImpl;
  }
  
  //등록
  @Override
  public Comment save(Comment comment) {
    comment.setId(++seq);

    LocalDateTime localDateTime = LocalDateTime.now();
    comment.setCdate(localDateTime);      //생성일시
    comment.setUdate(localDateTime);      //수정일시

    store.put(comment.getId(),comment);
    return comment;
  }

  //목록
  @Override
  public List<Comment> findAll() {
    return new ArrayList<>(store.values());
  }
  
  //조회
  @Override
  public Comment findById(Long id) {
    return store.get(id);
  }

  //삭제
  @Override
  public Comment delete(Long id) {
    return store.remove(id);      //삭제한 value반환
  }

  //수정
  @Override
  public Comment update(Long id, Comment comment) {
    comment.setId(id);
    comment.setUdate(LocalDateTime.now());
    return store.put(id, comment); //key에 대한 수정전의 comment반환
  }


  //저장소 clear 전체삭제
  @Override
  public void clearStore() {
    store.clear();
  }
}
