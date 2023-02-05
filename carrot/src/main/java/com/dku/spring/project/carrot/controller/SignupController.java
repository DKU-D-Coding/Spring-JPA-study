package com.dku.spring.project.carrot.controller;


import com.dku.spring.project.carrot.dto.UserSignupDto;
import com.dku.spring.project.carrot.entity.User;
import com.dku.spring.project.carrot.exception.DuplicateUserException;
import com.dku.spring.project.carrot.service.UserLoginService;
import com.dku.spring.project.carrot.service.UserSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
public class SignupController {

  private UserLoginService userLoginService;
  private UserSignupService userSignupService;

  @GetMapping("/api/user/signup")
  public String signUp(@ModelAttribute UserSignupDto userSignupDto){

    String username = userSignupDto.getUsername();
    String userpw = userSignupDto.getPassword();
    if(userLoginService.isExists(username, userpw)){
      throw new DuplicateUserException();
    }
    userSignupService.save(new User(username, userpw));

    return "signUpSuccess";
  }
}
