package com.project.carrot;

import com.project.carrot.domain.Member;
import com.project.carrot.domain.Service.MemberService;
import com.project.carrot.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception{
        Member member=new Member();
        member=member.builder()
                .UserEmail("t@5")
                .UserPass("1224").UserTel("01022-2").UserName("Yun")
                .NickName("Nick").Address("incheon").build();

        Long id=memberService.join(member);

        Assertions.assertThat(id).isEqualTo(member.getUserId());

        System.out.println(member);
    }

    @Test
    void 중복체크() throws Exception{
        Member member=new Member();
        member=member.builder()
                .UserEmail("t@5")
                .UserPass("1224").UserTel("01022-2").UserName("Yun")
                .NickName("Nick").Address("incheon").build();

        memberService.join(member);

        var ref = new Object() {
            Member member2 = new Member();
        };
        ref.member2 = ref.member2.builder()
                .UserEmail("t@5")
                .UserPass("1224").UserTel("01022-2").UserName("Yun")
                .NickName("Nick").Address("incheon").build();

        IllegalStateException e = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                ()->memberService.join(ref.member2));


        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 이메일입니다.");
    }

    @Test
    void 로그인() throws Exception{
        Member member=new Member();
        member=member.builder()
                .UserEmail("t@5")
                .UserPass("1224").UserTel("01022-2").UserName("Yun")
                .NickName("Nick").Address("incheon").build();

        Long id=memberService.join(member);

        IllegalStateException e= org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                ()->memberService.Login("t@56","1225"));

        Assertions.assertThat(e.getMessage()).isEqualTo("이메일이 일치하지 않습니다.");
    }
}
