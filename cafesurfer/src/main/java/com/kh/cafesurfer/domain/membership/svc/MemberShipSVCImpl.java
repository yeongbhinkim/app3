package com.kh.cafesurfer.domain.membership.svc;
import com.kh.cafesurfer.domain.membership.MemberShip;
import com.kh.cafesurfer.domain.membership.dao.MemberShipDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberShipSVCImpl implements MemberShipSVC{

  private final MemberShipDAO memberShipDAO;

  /**
   * 가입
   * @param memberShip
   * @return
   */
  @Override
  public MemberShip insertMember(MemberShip memberShip) {
    return memberShipDAO.insertMember(memberShip);
  }

  /**
   * 수정
   * @param memberShip
   */
  @Override
  public void updateMember(MemberShip memberShip) {
    memberShipDAO.updateMember(memberShip);
  }

  /**
   * 조회
   * @param memberEmail
   * @return
   */
  @Override
  public MemberShip selectMemberByEmail(String memberEmail) {
    return memberShipDAO.selectMemberByEmail(memberEmail);
  }

  /**
   * 조회
   * @param memberId
   * @return
   */
  @Override
  public MemberShip selectMemberByMemberId(Long memberId) {
    return memberShipDAO.selectMemberByMemberId(memberId);
  }

  /**
   * 전체조회
   * @return
   */
  @Override
  public List<MemberShip> selectAll() {
    return memberShipDAO.selectAll();
  }

  /**
   * 탈퇴
   * @param memberEmail
   */
  @Override
  public void deleteMember(String memberEmail) {
    memberShipDAO.deleteMember(memberEmail);
  }

  /**
   * 회원유무 체크
   * @param memberEmail
   * @return
   */
  @Override
  public boolean existMember(String memberEmail) {
    return memberShipDAO.existMember(memberEmail);
  }

  /**
   * 로그인
   * @param memberEmail
   * @param memberPasswd
   * @return
   */
  @Override
  public MemberShip login(String memberEmail, String memberPasswd) {
    return memberShipDAO.login(memberEmail,memberPasswd);
  }

  /**
   * 비밀먼호 일치여부체크
   * @param memberEmail
   * @param memberPasswd
   * @return
   */
  @Override
  public boolean isMember(String memberEmail, String memberPasswd) {
    return memberShipDAO.isMember(memberEmail,memberPasswd);
  }
}
