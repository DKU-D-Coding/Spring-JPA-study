package com.dku.spring.project.carrot.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginDto {

  @NotEmpty
  private final String username;

  @NotEmpty
  private final String password;
}
