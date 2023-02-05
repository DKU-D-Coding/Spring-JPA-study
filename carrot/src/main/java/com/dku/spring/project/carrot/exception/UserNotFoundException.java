package com.dku.spring.project.carrot.exception;


public class UserNotFoundException extends RuntimeException{

  public UserNotFoundException(){
    super("사용자 정보가 존재하지 않습니다.");
  }

}

