package com.kh.comment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //순서를 줄때
class CommentDaoImplTest {

  private static CommentDaoImpl store = CommentDaoImpl.getInstance();

  @BeforeAll
  static void beforeAll(){
    Comment comment = new Comment();
    comment.setEmail("test1@kh.com");
    comment.setNickname("테스터1");
    comment.setContent("댓글내용1");
    store.save(comment);

    comment = new Comment();
    comment.setEmail("test2@kh.com");
    comment.setNickname("테스터2");
    comment.setContent("댓글내용2");
    store.save(comment);
  }

  @Test
  @DisplayName("등록")
  @Order(1)
  void save() {
    Comment comment = new Comment();
    comment.setEmail("test3@kh.com");
    comment.setNickname("테스터3");
    comment.setContent("댓글내용3");
    store.save(comment);

    Assertions.assertThat(store.findAll().size()).isEqualTo(3);
  }

  @Test
  @DisplayName("목록")
  @Order(2)
  void findAll() {
    Assertions.assertThat(store.findAll().size()).isEqualTo(3);
  }

  @Test
  @DisplayName("조회")
  @Order(3)
  void findById() {
    Comment comment = store.findById(1L);
    Assertions.assertThat(comment.getEmail()).isEqualTo("test1@kh.com");
    Assertions.assertThat(comment.getNickname()).isEqualTo("테스터1");
    Assertions.assertThat(comment.getContent()).isEqualTo("댓글내용1");

  }

  @Test
  @DisplayName("삭제")
  @Order(4)
  void delete() {
    Long id = 2L;
    store.delete(id);
    Assertions.assertThat(store.findAll().size()).isEqualTo(2);
  }

  @Test
  @DisplayName("수정")
  @Order(5)
  void update() {
    Long id = 1L;
    Comment comment = store.findById(id);
    comment.setContent("수정후 내용");
    store.update(id,comment);

    Comment updatedComment = store.findById(id);
    Assertions.assertThat(updatedComment.getContent()).isEqualTo("수정후 내용");
  }

  @Test
  @DisplayName("저장소 clear")
  @Order(6)
  void clearStore() {
    store.clearStore();
    Assertions.assertThat(store.findAll()).isEmpty();
  }
}