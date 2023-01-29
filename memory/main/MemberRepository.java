package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    //optional = Null일 수 있는 변수를 감싸는 Wrapper 클래스
    List<Member> findAll();
}