package com.dku.spring.project.carrot.service;

import com.dku.spring.project.carrot.dto.UserInsertDto;
import com.dku.spring.project.carrot.entity.User;
import com.dku.spring.project.carrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public void insertUser(UserInsertDto dto){

    User user = User.builder()
        .loginId(dto.getId())
        .loginPw(dto.getPw())
        .build();

    userRepository.save(user);
  }
}
