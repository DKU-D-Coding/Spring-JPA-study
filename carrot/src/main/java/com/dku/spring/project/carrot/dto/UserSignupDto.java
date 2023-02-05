package com.dku.spring.project.carrot.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupDto {

  @NotEmpty
  private final String username;

  @NotEmpty
  private final String password;
}
