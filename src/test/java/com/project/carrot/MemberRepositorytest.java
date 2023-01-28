package com.project.carrot;

import com.project.carrot.domain.Member;
import com.project.carrot.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional

public class MemberRepositorytest {

    @Autowired//이거때문에 하
    MemberRepository memberRepository;
    @Test
    void 회원등록() throws Exception{

        Member member=new Member();
        member=member.builder()
                .UserEmail("t@52")
                .UserPass("12241").UserTel("01022-1").UserName("Yun1")
                .NickName("Nick1").Address("incheon1").build();

        memberRepository.save(member);

        Assertions.assertThat(member.getUserName()).isEqualTo("Yun1");

    }
}
