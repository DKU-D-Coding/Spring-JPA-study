package com.project.carrot.domain;

import com.project.carrot.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ExceptionDTO { //예외처리 DTO 생성
    private final Boolean success;
    private final HttpStatus status;
    private final String code;
    private final String message;

    public ExceptionDTO(CustomException e){ //예외가 발생하면 나타나는 것들
        this.success=false;
        this.status=e.getErrorCode().getStatus();
        this.code=e.getErrorCode().name();
        this.message=e.getErrorCode().getMessage();
    }

}
