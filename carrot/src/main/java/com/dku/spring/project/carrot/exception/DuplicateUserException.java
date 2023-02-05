package com.dku.spring.project.carrot.exception;

public class DuplicateUserException extends RuntimeException{

  public DuplicateUserException(){
    super("동일한 사용자 정보가 존재합니다.");
  }
}
