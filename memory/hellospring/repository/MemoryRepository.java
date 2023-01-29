package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import java.util.*;

//@Repository
public class MemoryRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    //동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //auto increment
        store.put(member.getId(), member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        //optional = Null일 수 있는 변수를 감싸는 Wrapper 클래스
        //Null이면 Optinal.empty()가 리턴됨
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //map을 list로 반환
    }
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() //store의 value로 반복문을 돌림
                .filter(member -> member.getName().equals(name))
                //map에 저장된 member의 이름중 인자로 받은 name과 동일한 값이 있는지 체크
                .findAny();
                //하나라도 찾으면 종료
    }
    public void clearStore() {
        store.clear();
    }
}