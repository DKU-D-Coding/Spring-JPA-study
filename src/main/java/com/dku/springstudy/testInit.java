package com.dku.springstudy;

import com.dku.springstudy.domain.user.User;
import com.dku.springstudy.domain.user.dto.SignUpRequestDTO;
import com.dku.springstudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class testInit {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final UserRepository userRepository;

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {

            User user = createUser("qwe", "123", "username", "01012341234", "usernick");
            em.persist(user);

        }

        private User createUser(String email, String password, String username, String phoneNumber, String nickname) {
            SignUpRequestDTO requestDTO = new SignUpRequestDTO();
            String bcryptPassword = passwordEncoder.encode(password);

            requestDTO.setEmail(email);
            requestDTO.setPassword(bcryptPassword);
            requestDTO.setUsername(username);
            requestDTO.setPhoneNumber(phoneNumber);
            requestDTO.setNickname(nickname);
            User user = requestDTO.toEntity();

            return user;
        }

    }
}
