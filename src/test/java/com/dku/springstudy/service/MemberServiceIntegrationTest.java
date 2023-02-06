package com.dku.springstudy.service;

import com.dku.springstudy.model.Member;
import com.dku.springstudy.model.Role;
import com.dku.springstudy.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Nested
    class SignupTest {
        @DisplayName("회원가입 테스트")
        @Test
        void joinTest() {
            Member member = new Member();
            member.setEmail("testets123@google.com");
            member.setPassword(passwordEncoder.encode("123123"));
            member.setName("김김김");
            member.setPhone("01000121234");
            member.setNickname("testestrqe");
            member.setRole(Role.USER);

            String memberEmail = memberService.join(member);

            Member foundMember = memberRepository.findByEmail(memberEmail).get();
            System.out.println(foundMember.getEmail());
            System.out.println(memberEmail);
            assertThat(foundMember.getEmail()).isEqualTo(memberEmail);
        }

        @DisplayName("이메일 중복되면 오류가 난다")
        @Test
        void joinTestByExistingEmail() {
            Member member = new Member();
            member.setEmail("testets123@google.com");
            member.setPassword(passwordEncoder.encode("123123"));
            member.setName("김김김");
            member.setPhone("01000121234");
            member.setNickname("testestrqe");
            member.setRole(Role.USER);
            memberService.join(member);

            Member member2 = new Member();
            member2.setEmail("testets123@google.com");
            member2.setPassword(passwordEncoder.encode("123123"));
            member2.setName("박박박");
            member2.setPhone("01012345678");
            member2.setNickname("testsrqwqe");
            member2.setRole(Role.USER);

            assertThatThrownBy(() -> memberService.join(member2))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}