package com.dku.springstudy.domain.user.service;

import com.dku.springstudy.domain.user.SecurityUser;
import com.dku.springstudy.domain.user.repository.SecurityUserRepository;
import com.dku.springstudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityUserRepository securityUserRepository;
    private final PasswordEncoder passwordEncoder;

    /*@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
        return new SecurityUser(user);
    }*/

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return securityUserRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(SecurityUser securityUser) {
        return User.builder()
                .username(securityUser.getUsername())
                .password(passwordEncoder.encode(securityUser.getPassword()))
                .roles(securityUser.getRoles().toArray(new String[0]))
                .build();
    }
}
