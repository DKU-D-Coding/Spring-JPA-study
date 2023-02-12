package com.dku.springstudy.security;

import com.dku.springstudy.model.User;
import com.dku.springstudy.exception.CustomException;
import com.dku.springstudy.exception.ErrorCode;
import com.dku.springstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND_ERROR));
        return new CustomUserDetails(user);
    }
}
