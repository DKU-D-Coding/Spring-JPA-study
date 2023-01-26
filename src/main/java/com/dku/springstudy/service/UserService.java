package com.dku.springstudy.service;

import com.dku.springstudy.domain.User;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long join(User user){
        userRepository.findByName(user.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        });
        userRepository.save(user);
        return user.getId();
    }

    public Optional<User> findOne(Long id){
        return userRepository.findById(id);
    }


}
