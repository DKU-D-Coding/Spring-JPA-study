package hello.hellospring;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //인터페이스만 만들면 스프링이 구현체를 만들어 bean에 등록

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
}
