package com.kh.cafesurfer.web;
import com.kh.cafesurfer.domain.membership.MemberShip;
import com.kh.cafesurfer.domain.membership.svc.MemberShipSVC;
import com.kh.cafesurfer.web.form.memberShip.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberShipController {

  private final MemberShipSVC memberShipSVC;

  //성별
  @ModelAttribute("gender")
  public MemberGender[] genter(){

    return MemberGender.values();  //[MALE, FEMALE]
  }

  //회원가입
  @GetMapping("/join")
  public String joinForm(@ModelAttribute JoinForm joinForm){
    log.info("joinForm() 호출됨!");
    return "memberShip/joinForm";
  }

  //회원가입처리
  @PostMapping("/join")
  public String join(
      @Valid @ModelAttribute JoinForm joinForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes){

    log.info("join() 호출됨!");
    log.info("MemberEmail={}, MemberPasswd={}, MemberPasswdChk={}, MemberName={}",
        joinForm.getMemberEmail(),joinForm.getMemberPasswd(),
        joinForm.getMemberPasswdChk(),joinForm.getMemberName());

    //1)유효성체크 - 필드오류
    if(bindingResult.hasErrors()){
      log.info("error={}", bindingResult);
      return "memberShip/joinForm";
    }
    //2)아이디 중복체크
    if(memberShipSVC.existMember(joinForm.getMemberEmail())){
      bindingResult.rejectValue("getMemberEmail","joinForm.MemberEmail.dup");
      log.info("error={}", bindingResult);
      return "memberShip/joinForm";
    }
    //3)유효성체크 - global 오류 (2개이상의 필드체크, 백앤드로직 수행시 발생오류)
    //비밀번호 != 비빌번호체크
    if(!joinForm.getMemberPasswd().equals(joinForm.getMemberPasswdChk()))   {
      bindingResult.reject("member.MemberPasswdChk");
      return "memberShip/joinForm";
    }

    //4)정상처리로직
    log.info("joinForm={}", joinForm);
    MemberShip memberShip = new MemberShip( null,
        joinForm.getMemberEmail(), joinForm.getMemberPasswd(), joinForm.getMemberName(),
        joinForm.getMemberGender().getDescription(),joinForm.getMemberAge(),joinForm.getMemberTel(),null);

    MemberShip joinedMember = memberShipSVC.insertMember(memberShip);
    log.info("MemberEmail={}, MemberPasswd={}, MemberName={}",
        joinedMember.getMemberEmail(),joinedMember.getMemberPasswd(),joinedMember.getMemberName());

    return "redirect:/members/joinSuccess";
  }

//  //문자열 리스트를 ','를 구분자로하는 문자열 변환
//  private String makeListToString(List<String> hobby) {
//    StringBuffer str = new StringBuffer();
//    for (int i=0; i<hobby.size(); i++) {
//      str.append(hobby.get(i));
//      if(i == hobby.size()-1) break;
//      str.append(",");
//    }
//    return str.toString();
//  }

  @GetMapping("/joinSuccess")
  public String joinSuccess(){
    return "memberShip/joinSuccess";
  }

  //회원수정
  @GetMapping("/{memberEmail}/modify")
  public String modifyForm(
      @PathVariable("MemberEmail") String MemberEmail,
      Model model){

    MemberShip memberShip = memberShipSVC.selectMemberByEmail(MemberEmail);

    ModifyForm modifyForm = new ModifyForm();
    modifyForm.setMemberEmail(memberShip.getMemberEmail());
    modifyForm.setMemberName(memberShip.getMemberName());
    modifyForm.setMemberGender(getGender(memberShip.getMemberGender()));
    modifyForm.setMemberAge(memberShip.getMemberAge());
    modifyForm.setMemberTel(memberShip.getMemberTel());

    model.addAttribute("modifyForm", modifyForm);

    return "memberShip/modifyForm";
  }

  //문자열로 Enum객체에서 상수요소 찾아오기
  private MemberGender getGender(String gender) {
    MemberGender finded = null;
    for(MemberGender g : MemberGender.values()){
      if(g.getDescription().equals(gender)){
        finded = MemberGender.valueOf(g.name());
        break;
      }
    }
    return finded;
  }
  //회원수정 처리
  @PostMapping("/modify")
  public String modify(
      @Valid @ModelAttribute ModifyForm modifyForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes){

    //1) 유효성 체크  - 필드오류
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "memberShip/modifyForm";
    }

    //2) 비밀번호가 일치하는지 체크
    if(!memberShipSVC.isMember(modifyForm.getMemberEmail(), modifyForm.getMemberPasswd())){
      bindingResult.rejectValue("MemberPasswd","memberShip.MemberPasswdChk");
      log.info("bindingResult={}", bindingResult);
      return "memberShip/modifyForm";
    }

    //3) 회원정보 수정
    MemberShip memberShip = new MemberShip( null,
        modifyForm.getMemberEmail(), modifyForm.getMemberPasswd(), modifyForm.getMemberName(),
        modifyForm.getMemberGender().getDescription(),
        modifyForm.getMemberAge(),
        modifyForm.getMemberTel(),null);

    memberShipSVC.updateMember(memberShip);
    redirectAttributes.addAttribute("MemberEmail", memberShip.getMemberEmail());

    return "redirect:/members/{memberEmail}/detail";  //회원 상세화면 이동
  }

  //회원상세
  @GetMapping("/{memberEmail}/detail")
  public String detail(@PathVariable String memberEmail, Model model){

    MemberShip memberShip = memberShipSVC.selectMemberByEmail(memberEmail);

    DetailForm detailForm = new DetailForm();
    detailForm.setMemberEmail(memberShip.getMemberEmail());
    detailForm.setMemberName(memberShip.getMemberName());
    detailForm.setMemberGender(getGender(memberShip.getMemberGender()));
    detailForm.setMemberAge(memberShip.getMemberAge());
    detailForm.setMemberTel(memberShip.getMemberTel());

    model.addAttribute("detailForm", detailForm);

    return "memberShip/detailForm";
  }

  //회원탈퇴
  @GetMapping("/{memberEmail}/out")
  public String outForm(@ModelAttribute OutForm outForm ){
    log.info("outForm 호출됨!");
    return "memberShip/outForm";
  }

  @PostMapping("/out")
  public String out(
      @Valid @ModelAttribute OutForm outForm,
      BindingResult bindingResult,
      HttpSession session){

    log.info("out 호출됨");
    //1)유효성체크
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "/memberShip/outForm";
    }
    //2)동의 체크여부
    if(!outForm.getAgree()){
      bindingResult.rejectValue("agree",null, "탈퇴 안내를 확인하고 동의해 주세요.");
      return "/memberShip/outForm";
    }

    //3) 비밀번호가 일치하는지 체크
    if(!memberShipSVC.isMember(outForm.getMemberEmail(), outForm.getMemberPasswd())){
      bindingResult.rejectValue("MemberPasswd","memberShip.MemberPasswdChk");
      log.info("bindingResult={}", bindingResult);
      return "memberShip/outForm";
    }

    //4) 탈퇴로직 수행
    //4-1) 회원정보 삭제
    memberShipSVC.deleteMember(outForm.getMemberEmail());
    //4-2) 센션정보 제거
    if(session != null){
      session.invalidate();
    }
    return "redirect:/members/outCompleted";
  }

  @GetMapping("/outCompleted")
  public String outCompleted(){

    return "memberShip/outCompleted"; //탈퇴수행 완료 view
  }

  //마이페이지
  @GetMapping("/myinfo")
  public String myinfo(){
    log.info("myinfo() 호출됨");
    return "memberShip/myinfo";
  }

  //아이디 찾기
  @GetMapping("/findEmail")
  public String findEmail(){

    return "memberShip/findEmail";
  }
}
