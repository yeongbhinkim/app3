package com.kh.comment.domain;

import org.junit.jupiter.api.*;

public class JunitLifeCycle {

  @BeforeAll
  static void beforeAll(){
    System.out.println("beforeAll()");
  }

  @BeforeEach
  void beforeEach(){
    System.out.println("beforeEach()");
  }

  @AfterEach
  void AfterEach(){
    System.out.println("afterEach()");
  }

  @Test
  void method1(){
    System.out.println("method1()");
  }
  @Test
  void method2(){
    System.out.println("method2()");
  }
  @Test
  void method3(){
    System.out.println("method3()");
  }

  @AfterAll
  static void AfterAll(){
    System.out.println("afterAll()");
  }
}



