package com.dku.springstudy;

import com.dku.springstudy.repository.JdbcMemberRepository;
import com.dku.springstudy.repository.JdbcTemplateMemberRepository;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.repository.MemoryMemberRepository;
import com.dku.springstudy.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
