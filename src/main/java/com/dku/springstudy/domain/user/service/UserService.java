package com.dku.springstudy.domain.user.service;

import com.dku.springstudy.domain.exception.UsernameAlreadyExistException;
import com.dku.springstudy.domain.user.User;
import com.dku.springstudy.domain.user.dto.UserRequestDTO;
import com.dku.springstudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
/*    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(UserRequestDTO userRequestDTO) throws IllegalArgumentException, UsernameAlreadyExistException {
        validate(userRequestDTO);
        User user = userRequestDTO.toEntity();
        user.(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    private void validate(UserRequestDTO userDTO) throws IllegalArgumentException, UsernameAlreadyExistException {
        if (userDTO.getUsername().isBlank() || userDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException();
        }
    }*/
}
