package com.dku.springstudy.service;

import com.dku.springstudy.domain.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Test
    void membership(){
        //given
        Member member = new Member();
        member.setName("jaem");

        //when
        Long Id = memberService.membership(member);
        Member findMember = memberService.findOne(Id).get();

        //then
        Assertions.assertEquals(member.getName(), findMember.getName());

    }

}
