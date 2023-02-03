package com.dku.springstudy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dku.springstudy.domain.Member;
import com.dku.springstudy.repository.MemberRepository;
import com.dku.springstudy.repository.MemoryMemberRepository;

@SpringBootTest
class SpringStudyApplicationTests {

    MemberRepository memberRepository = new MemoryMemberRepository();

    @Test
    void 회원가입() {
        // 멤버 저장
        Member member = new Member();
        member.setName("skyepodium");
        memberRepository.save(member);

        // 저장한 멤버 아이디로 검색
        Member findMember = memberRepository.findById(member.getId()).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }
}
