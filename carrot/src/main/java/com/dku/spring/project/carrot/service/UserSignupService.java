package com.dku.spring.project.carrot.service;

import com.dku.spring.project.carrot.entity.User;
import com.dku.spring.project.carrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignupService {

  private final UserRepository userRepository;

  public void save(User user){
    userRepository.save(user);
  }
}