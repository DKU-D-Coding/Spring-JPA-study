package com.dku.spring.project.carrot.service;

import com.dku.spring.project.carrot.dto.UserDto;
import com.dku.spring.project.carrot.exception.UserNotFoundException;
import com.dku.spring.project.carrot.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username){

    UserDto userDto = userRepository.findByLoginId(username)
        .orElseThrow(() -> new UserNotFoundException());

    String loginId = userDto.getLoginId();
    String loginPw = userDto.getLoginPw();

    return new User(loginId, loginPw, Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER")));
  }
}
