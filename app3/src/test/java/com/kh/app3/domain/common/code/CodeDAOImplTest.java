package com.kh.app3.domain.common.code;

import com.kh.app3.web.Code;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CodeDAOImplTest {

  @Autowired
  private CodeDAO codeDAO;

  @Test
  void code() {
    List<Code> codes = codeDAO.code("B01");

    log.info(codes.toString());

//    codes.stream().findFirst()
  }
}