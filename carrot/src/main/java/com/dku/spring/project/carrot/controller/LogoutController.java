package com.dku.spring.project.carrot.controller;

import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

  @Autowired
  RedisTemplate redisTemplate;

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response){

    Cookie cookie = Arrays.stream(request.getCookies())
        .filter(c -> c.getName().equals("SESSION"))
        .findFirst()
        .orElse(null);

    if(Objects.isNull(cookie)){
      String sessionId = cookie.getValue();

      cookie.setMaxAge(0);
      response.addCookie(cookie);

      redisTemplate.opsForHash().delete(sessionId, "username", "authority");
    }

    return "redirect:/login";
  }
}

