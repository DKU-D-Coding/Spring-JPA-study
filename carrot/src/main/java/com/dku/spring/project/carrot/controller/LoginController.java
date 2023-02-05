package com.dku.spring.project.carrot.controller;


import com.dku.spring.project.carrot.dto.UserLoginDto;
import com.dku.spring.project.carrot.exception.UserNotFoundException;
import com.dku.spring.project.carrot.exception.ValidationFailedException;
import com.dku.spring.project.carrot.service.UserLoginService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller("/api/user")
@RequiredArgsConstructor
public class LoginController {

  private final UserLoginService userLoginService;

  @GetMapping("/login")
  public String login(@SessionAttribute(value = "LOGINID", required = false) String loginId){
    if(Objects.isNull(loginId)){
      return "loginForm";
    }
    return "loginSuccess";
  }

  @PostMapping("/login")
  public String doLogin(@ModelAttribute UserLoginDto dto, BindingResult bindingResult,
      HttpServletRequest request, ModelMap modelMap){

    if(bindingResult.hasErrors()){
      throw new ValidationFailedException(bindingResult);
    }

    String username = dto.getUsername();
    String password = dto.getPassword();

    if(userLoginService.isExists(username, password)){
      HttpSession session = request.getSession();
      session.setAttribute("LOGINID", username);
      modelMap.put("username", username);
      return "loginSuccess";
    }
    throw new UserNotFoundException();
  }

  @ExceptionHandler(UserNotFoundException.class)
  public String hanldeUserNotFoundException(){
    return "redirect:/api/user/login";
  }
}

