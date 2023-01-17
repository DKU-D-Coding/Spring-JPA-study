package com.dku.springstudy;

import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.repository.MemoryMemberRepository;
import com.dku.springstudy.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
