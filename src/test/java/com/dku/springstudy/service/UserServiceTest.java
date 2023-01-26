package com.dku.springstudy.service;

import com.dku.springstudy.domain.User;
import com.dku.springstudy.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void join(){
        //given
        User user = new User();
        user.setName("jaem");
        user.setAddress("경기도 용인시 죽전동");
        user.setEmail("void0629@gmail.com");
        user.setCreated(new Timestamp(System.currentTimeMillis()));
        user.setUpdated(new Timestamp(System.currentTimeMillis()));
        user.setPassword("0629");
        user.setPhone("01099087014");
        user.setStatus("normal");

        //when
        Long Id = userService.join(user);
        User findUser = userService.findOne(Id).get();

        //then
        Assertions.assertEquals(user.getName(), findUser.getName());

    }
}
