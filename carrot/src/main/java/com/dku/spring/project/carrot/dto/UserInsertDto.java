package com.dku.spring.project.carrot.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInsertDto {

  @NotBlank
  public String id;

  @NotBlank
  public String pw;
}

