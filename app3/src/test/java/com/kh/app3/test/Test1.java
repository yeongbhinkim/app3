package com.kh.app3.test;

import com.kh.app3.web.form.member.Gender;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Test1 {

  @Test
  @DisplayName("문자열을 요소로 갖는 리스트를 콤마로 구분자로 하는 문자열로 변환")
  @Disabled
  void test1(){
    List<String> hobby = new ArrayList<>();
    hobby.add("a");
    hobby.add("b");
    hobby.add("c");
    hobby.add("d");
    String s = makeListToString(hobby);
    Assertions.assertThat("a,b,c,d").isEqualTo(s);
  }

  private String makeListToString(List<String> hobby) {
    StringBuffer str = new StringBuffer();
    for (int i=0; i<hobby.size(); i++) {
      str.append(hobby.get(i));
      if(i == hobby.size()-1) break;
      str.append(",");
    }
    return str.toString();
  }

  @Test
  @DisplayName("콤마를 구분자로 하는 문자열을 문자열을 요소로 갖는 리스로 변환")
  void test2() {
    String str = "a,b,c,d"; // str => List<String>

    List<String> list = stringToList(str);
    log.info("list={}",list);
  }

  private List<String> stringToList(String str) {
    String[] array = str.split(",");
    log.info("array={}", array.length);
    List<String> list = new ArrayList<>();
    for(int i=0; i<array.length; i++) {
      list.add(array[i]);
    }
    return list;
  }


  @Test()
  @DisplayName("문자열로 Enum 객체에서 상수요소 찾아오기")
  void test3(){
    Gender g1 = Gender.MALE;
    Gender g2 = Gender.FEMALE;

//    for(Gender g : Gender.values()) {
//      log.info(g.name());
//      log.info(g.getDescription());
//    };

    String gender = "남자";
    Gender finded = null;
    finded = getGender(gender);
    log.info(finded.name());
  }

  private Gender getGender(String gender) {
    Gender finded = null;
    for(Gender g : Gender.values()) {
      if(g.getDescription().equals(gender)) {
        finded = Gender.valueOf(g.name());
        break;
      }
    }
    return finded;
  }


}