package com.kh.cafesurfer.domain.membership.dao;

import com.kh.cafesurfer.domain.membership.MemberShip;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest  // springboot 환경에서 테스트 진행
class MemberShipDAOImplTest {

  @Autowired  // SC(스프링컨테이너)에서 동일타입의 객체를 찾아서 주입시켜준다.
  private MemberShipDAO memberShipDAO;

  @Test
  @DisplayName("등록") //테스트케이스 이름
  void insertMember() {
    MemberShip memberShip = new MemberShip("test3@kh.com","1234","테스터abc","남",22L,"010-5555-5555");
    MemberShip insertMember = memberShipDAO.insertMember(memberShip);
    insertMember.setMemberId(null);
    Assertions.assertThat(insertMember).usingRecursiveComparison().isEqualTo(memberShip);
  }

  @Test
  @DisplayName("수정")
  void updateMember() {

    String memberEmail = "test3@kh.com";
    String memberPasswd = "12345";
    String memberName = "테스터3";
    String memberGender = "여";
    Long memberAge = 25L;
    String memberTel = "010-7777-7777";

    MemberShip memberShip = new MemberShip(memberEmail,memberPasswd,memberName,memberGender,memberAge,memberTel);
    memberShipDAO.updateMember(memberShip);

    MemberShip updatedMember = memberShipDAO.selectMemberByEmail(memberEmail);

    log.info("updatedMember.memberName={}",memberName);
    Assertions.assertThat(updatedMember.getMemberName()).isEqualTo(memberName);

  }

  @Test
  @DisplayName("조회")
  void selectMemberByEmail() {
    String memberEmail ="test3@kh.com";
    MemberShip memberShip = memberShipDAO.selectMemberByEmail(memberEmail);
    Assertions.assertThat(memberShip.getMemberEmail()).isEqualTo("test3@kh.com");
  }

  @Test
  void selectMemberByMemberId() {
    Long memberId = 3L;
    MemberShip memberShip = memberShipDAO.selectMemberByMemberId(memberId);
    log.info("memberShip={}", memberShip);
    Assertions.assertThat(memberShip.getMemberId()).isEqualTo(memberId);
  }

  @Test
  @DisplayName("전체조회")
  void selectAll() {
    List<MemberShip> memberShip = memberShipDAO.selectAll();
    log.info("memberShip={}", memberShip);
    Assertions.assertThat(memberShip.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("탈퇴")
  void deleteMember() {

    String email = "test3@kh.com";
    memberShipDAO.deleteMember(email);

    boolean isMember = memberShipDAO.existMember(email);
    Assertions.assertThat(isMember).isFalse();

  }

  @Test
  @DisplayName("로그인")
  void login() {
    String memberEmail = "test3@kh.com";
    String memberPasswd = "12345";

    MemberShip memberShip = memberShipDAO.login(memberEmail,memberPasswd);

    Assertions.assertThat(memberShip).isNotNull();
  }

  @Test
  void existMember() {

  }

  @Test
  void isMember() {

  }


}