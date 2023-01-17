package com.dku.springstudy;

import com.dku.springstudy.repository.*;
import com.dku.springstudy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired //생략가능
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * private EntityManager em;
     * <p>
     * public SpringConfig(EntityManager em) {
     * this.em = em;
     * }
     * <p>
     * private final DataSource dataSource;
     * <p>
     * public SpringConfig(DataSource dataSource) {
     * this.dataSource = dataSource;
     * }
     */

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    /**
     @Bean public MemberRepository memberRepository(){
     //return new MemoryMemberRepository();
     //return new JdbcMemberRepository(dataSource);
     //return new JdbcTemplateMemberRepository(dataSource);
     return new JpaMemberRepository(em);
     }
     */
}
