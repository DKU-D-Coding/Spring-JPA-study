package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryRepository;

import java.util.List;
import java.util.Optional;

//@service
public class MemberService {
    //private final MemberRepository memberRepository = new MemoryRepository();
    private final MemberRepository memberRepository;
    //@Autowired
    public MemberService(MemberRepository memberRepository) { //의존관계 추가
        this.memberRepository = memberRepository;
    }
    public Long join(Member member) { // 회원가입
        validateDuplicateMember(member); //중복 회원 체크
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //optinal 값이 null이 아니면
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() { //전체 회원 조회
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}