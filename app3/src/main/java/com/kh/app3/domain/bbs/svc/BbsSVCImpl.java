package com.kh.app3.domain.bbs.svc;

import com.kh.app3.domain.bbs.dao.Bbs;
import com.kh.app3.domain.bbs.dao.BbsDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BbsSVCImpl implements BbsSVC{

  private final BbsDAO bbsDAO;

  //원글
  @Override
  public Long saveOrigin(Bbs bbs) {
    return bbsDAO.saveOrigin(bbs);
  }
  //목록
  @Override
  public List<Bbs> findAll() {
    return bbsDAO.findAll();
  }
  //상세조회
  @Override
  public Bbs findByBbsId(Long id) {
    Bbs findedItem = bbsDAO.findByBbsId(id);
    bbsDAO.increaseHitCount(id);
    return findedItem;
  }
  //삭제
  @Override
  public int deleteBbsId(Long id) {
    return bbsDAO.deleteBbsId(id);
  }
  //수정
  @Override
  public int updateByBbsId(Long id, Bbs bbs) {
    return bbsDAO.updateByBbsId(id,bbs);
  }
  //답글작성
  @Override
  public Long saverReply(Long pbbsId, Bbs replybbs) {
    return bbsDAO.saverReply(pbbsId,replybbs);
  }
  //전체건수
  @Override
  public int totalCount() {
    return bbsDAO.totalCount();
  }
}
