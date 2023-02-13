package com.project.carrot.controller;

import com.project.carrot.CustomException;
import com.project.carrot.domain.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //@ControllerAdvice + @ResponseBody
//@ControllerAdvice는 컨트롤러에서 발생하는 에러를 처리 //SecurityFilter에서 발생하는 예외는 스프링의 것이 아니므로 처리하지 못함
//@ResponseBody: 응답값 String->Stirng, Object->Json 형식으로 반환해준다.
//@ExceptionHandler: 특정 예외처리를 지정하여 관리
public class ControllerAdvisor {
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity exceptionHandler(CustomException e){
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(new ExceptionDTO(e));
    }
}
