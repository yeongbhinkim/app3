package com.kh.app3.web;

import com.kh.app3.domain.bbs.dao.Bbs;
import com.kh.app3.domain.bbs.svc.BbsSVC;
import com.kh.app3.domain.common.code.CodeDAO;
import com.kh.app3.domain.common.file.UploadFile;
import com.kh.app3.domain.common.file.svc.UploadFileSVC;
import com.kh.app3.domain.common.paging.PageCriteria;
import com.kh.app3.web.form.bbs.*;
import com.kh.app3.web.form.login.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class BbsController {
  private final BbsSVC bbsSVC;
  private final CodeDAO codeDAO;
  private final UploadFileSVC uploadFileSVC;

  @Autowired
  @Qualifier("pc10")
  private PageCriteria pc;

//게시판 코드,디코드 가져오기
  @ModelAttribute("classifier")
  public List<Code> classifier(){
    return codeDAO.code("B01");
  }

  //작성양식
//  @GetMapping("/add")
//  public String addForm(Model model){
//    model.addAttribute("addForm", new AddForm());
//    return "bbs/addForm";
//  }
  @GetMapping("/add")
  public String addForm(Model model,
                        HttpSession session){
    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

    AddForm addForm = new AddForm();
    addForm.setEmail(loginMember.getEmail());
    addForm.setNickname(loginMember.getNickname());
    model.addAttribute("addForm", addForm);

    return "bbs/addForm";
  }

  //작성처리
  @PostMapping("/add")
  public String add(
//                    @Valid
                    @ModelAttribute AddForm addForm,  // @Valid : 유효성체크
                    BindingResult bindingResult,  //폼객체에 바인딩될때 오류내용이 저장되는 객체
                    HttpSession session,
                    RedirectAttributes redirectAttributes) throws IOException {
    log.info("addForm={}",addForm);

//     유효성체크 로직
    if (bindingResult.hasErrors()){
      log.info("add/bindingResult={}", bindingResult);
      return "bbs/addForm";
    }
    Bbs bbs = new Bbs();
    BeanUtils.copyProperties(addForm,bbs);
    //세션 가져오기
    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
    //세션 정보가 없으면 로그인페이지로 이동
    if(loginMember == null){
      return "redirect:/login";
    }
    //세션에서 이메일, 별칭가져오기
    bbs.setEmail(loginMember.getEmail());
    bbs.setNickname(loginMember.getNickname());

    Long originId = 0l;
    //파일첨부유무
    if(addForm.getFiles() == null) {
      originId = bbsSVC.saveOrigin(bbs);
    }else{
      originId = bbsSVC.saveOrigin(bbs, addForm.getFiles());
    }
    redirectAttributes.addAttribute("id",originId);

//     <= 서버응답 302 get http://서버:port/bbs/10
//     => 클라이언트요청 get http://서버:port/bbs/10
    return "redirect:/bbs/{id}";
  }

  //목록
  @GetMapping({"/list"})
  public String list(Model model){

    List<Bbs> list = bbsSVC.findAll();

    List<ListForm> partOfList = new ArrayList<>();
    for (Bbs bbs : list){
      ListForm listForm = new ListForm();
      BeanUtils.copyProperties(bbs,listForm);
      partOfList.add(listForm);
    }

    model.addAttribute("list",partOfList);

    return "bbs/list";
  }

  @GetMapping({"/list/{reqPage}"})
  public String listAndReqPage(
      @PathVariable Integer reqPage,
      @RequestParam(required = false) String category,
      Model model){

    //요청페이지
    pc.getRc().setReqPage(reqPage);

    List<Bbs> list = null;
    //게시물 목록 전체
    if (category == null) {
      //총레코드수
      pc.setTotalRec(bbsSVC.totalCount());
      list = bbsSVC.findAll(pc.getRc().getStartRec(), pc.getRc().getEndRec());
    
      //카테고리별 목록
    } else { 
      //총레코드수
      pc.setTotalRec(bbsSVC.totalCount(category));
      list = bbsSVC.findAll(category, pc.getRc().getStartRec(), pc.getRc().getEndRec());

    }

    List<ListForm> partOfList = new ArrayList<>();
    for (Bbs bbs : list){
      ListForm listForm = new ListForm();
      BeanUtils.copyProperties(bbs,listForm);
      partOfList.add(listForm);
    }

    model.addAttribute("list",partOfList);
    model.addAttribute("pc",pc);

    return "bbs/list";
  }

 
  
  //조회
//  @ResponseBody //제이슨
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id,
                       Model model){

    Bbs detailBbs = bbsSVC.findByBbsId(id);

    DetailForm detailForm = new DetailForm();

//    detailForm.setBcategory(detailBbs.getBcategory());
//    detailForm.setTitle(detailBbs.getTitle());
//    detailForm.setBcontent(detailBbs.getBcontent());
//    detailForm.setEmail(detailBbs.getEmail());
//    detailForm.setNickname(detailBbs.getNickname());
//    detailForm.setHit(detailBbs.getHit());

    BeanUtils.copyProperties(detailBbs,detailForm);
    model.addAttribute("detailForm",detailForm);

    //첨부조회
    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(detailBbs.getBcategory(), detailBbs.getBbsId());
    if(attachFiles.size() > 0){
      log.info("attachFiles={}",attachFiles);
      model.addAttribute("attachFiles", attachFiles);
    }

//      return detailForm;
    return "bbs/detailForm";
  }
  //삭제
  @GetMapping("/{id}/del")
  public String del(@PathVariable Long id){

    bbsSVC.deleteBbsId(id);

    return "redirect:/bbs";
  }
  //수정양식
  @GetMapping("/{id}/edit")
  public String editFrom(@PathVariable Long id,Model model){

    Bbs bbs = bbsSVC.findByBbsId(id);

    EditForm editForm = new EditForm();
    BeanUtils.copyProperties(bbs,editForm);
    model.addAttribute("editForm", editForm);

    log.info("editForm={}",editForm);
    //첨부조회
    List<UploadFile> attachFiles = uploadFileSVC.getFilesByCodeWithRid(bbs.getBcategory(), bbs.getBbsId());
    if(attachFiles.size() > 0){
      log.info("attachFiles={}",attachFiles);
      model.addAttribute("attachFiles", attachFiles);
    }

    return "bbs/editForm";
  }
  //수정처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id,
                     @Valid @ModelAttribute EditForm editForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes
  ){
    if(bindingResult.hasErrors()){
      return "bbs/editForm";
    }

    Bbs bbs = new Bbs();
    BeanUtils.copyProperties(editForm, bbs);
    bbsSVC.updateByBbsId(id,bbs);

    if(editForm.getFiles() == null) {
      bbsSVC.updateByBbsId(id, bbs);
    }else{
      bbsSVC.updateByBbsId(id, bbs, editForm.getFiles());
    }
    redirectAttributes.addAttribute("id",id);
    return "redirect:/bbs/{id}";
  }
  //답글작성양식
  @GetMapping("/{id}/reply")
  public String replyForm(@PathVariable Long id,
                          Model model,
                          HttpSession session){
    Bbs parentBbs = bbsSVC.findByBbsId(id);
    ReplyForm replyForm = new ReplyForm();
    replyForm.setBcategory(parentBbs.getBcategory());
    replyForm.setTitle("답글:"+parentBbs.getTitle());

    // 세션에서 로그인정보 가져오기
    LoginMember loginMember = (LoginMember) session.getAttribute(SessionConst.LOGIN_MEMBER);
    replyForm.setEmail(loginMember.getEmail());
    replyForm.setNickname(loginMember.getNickname());
    model.addAttribute("replyForm",replyForm);
    return "bbs/replyForm";
  }
  //답글작성처리
  @PostMapping("/{id}/reply")
  public String reply(@PathVariable Long id,   //부모글 bbsId
                      @Valid ReplyForm replyForm,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes
                      ){
    if(bindingResult.hasErrors()){

      return "bbs/replyForm";
    }
    Bbs replyBbs = new Bbs();
    BeanUtils.copyProperties(replyForm, replyBbs);

    //부모글의 bbsId,bgroup, step, bindent 참조
    appendInfoOfparentBbs(id,replyBbs);
    //답글저장(return) 답글번호
    Long replyBbsId = bbsSVC.saverReply(id, replyBbs);

    redirectAttributes.addAttribute("id",replyBbsId);
    return "redirect:/bbs/{id}";
  }

  private void appendInfoOfparentBbs(Long parentBbsId, Bbs replyBbs) {
    Bbs parentBbs = bbsSVC.findByBbsId(parentBbsId);
    replyBbs.setBcategory(parentBbs.getBcategory());
    replyBbs.setPbbsId(parentBbs.getBbsId());
    replyBbs.setBgroup(parentBbs.getBgroup());
    replyBbs.setStep(parentBbs.getStep());
    replyBbs.setBindent(parentBbs.getBindent());
  }
}
