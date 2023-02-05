package com.dku.spring.project.carrot.exception;

import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;

public class ValidationFailedException extends RuntimeException{

  public ValidationFailedException(BindingResult bindingResult){
    super(bindingResult.getAllErrors()
        .stream()
        .map(error -> new StringBuilder().append("ObjectName=").append(error.getObjectName())
            .append(",Message=").append(error.getDefaultMessage())
            .append(",code=").append(error.getCode()))
        .collect(Collectors.joining(" | ")));
  }
}
