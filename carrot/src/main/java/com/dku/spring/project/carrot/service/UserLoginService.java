package com.dku.spring.project.carrot.service;

import com.dku.spring.project.carrot.dto.UserDto;
import com.dku.spring.project.carrot.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

  private final UserRepository userRepository;

  public Optional<UserDto> getUserByName(String username){
    return userRepository.findByLoginId(username);
  }


  public boolean isExists(String username, String password){
    Optional<UserDto> user = getUserByName(username);

    if(user.isPresent()){
      return password.equals(user.get().getLoginPw());
    }
    return false;
  }
}
