package com.kh.cafesurfer.domain.membership.dao;

import com.kh.cafesurfer.domain.membership.MemberShip;

import java.util.List;

public interface MemberShipDAO {

  //가입
  MemberShip insertMember(MemberShip memberShip);
  //수정
  void updateMember(MemberShip memberShip);
  // 조회 by email
  MemberShip selectMemberByEmail(String memberEmail);
  // 조회 by member_id
  MemberShip selectMemberByMemberId(Long memberId);
  //전체조회
  List<MemberShip> selectAll();
  //탈퇴
  void deleteMember(String memberEmail);
  //회원유무체크
  boolean existMember(String memberEmail);
  //로그인 인증
  MemberShip login(String memberEmail, String memberPasswd);
  //비밀번호 일치여부 체크
  boolean isMember(String memberEmail, String memberPasswd);

}
