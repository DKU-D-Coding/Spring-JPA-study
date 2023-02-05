package com.dku.spring.project.carrot.security;


import com.dku.spring.project.carrot.dto.UserDto;
import com.dku.spring.project.carrot.service.UserLoginService;
import com.dku.spring.project.carrot.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements
    AuthenticationSuccessHandler {

  @Autowired
  RedisTemplate redisTemplate;

  //언제 실행
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication)
      throws ServletException, IOException {
    // principal이 userDetails, Credentials가 비밀번호, Authorities가 권한

    String sessionId = UUID.randomUUID().toString();

    Cookie cookie = new Cookie("SESSION", sessionId);
    cookie.setMaxAge(259200);

    response.addCookie(cookie);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    String authority = new ArrayList<>(userDetails.getAuthorities()).get(0).getAuthority();

    redisTemplate.opsForHash().put(sessionId, "username", username);
    redisTemplate.opsForHash().put(sessionId, "authority", authority);

    super.onAuthenticationSuccess(request, response, authentication);
  }
}