package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member){
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){  //Optional은 하나의 통. 찾으려는 객체가 있을 수도있고 없을 수도 있다 자바8부터 나온거
//        List<Member> all = findAll();
//        for (Member m : all) {
//            if(m.getLoginId().equals(loginId)){
//                return Optional.of(m);    // 있으면 m을 반환
//            }
//        }
//        return Optional.empty();   // 없으면 empty 반환  예전에는 null을 직접 반환했는데 요즘에는 Optional로 empty를 반환해줌

        return findAll().stream()    // list를 stream으로 바꿈 루프를 돈다고 생각
                .filter(m -> m.getLoginId().equals(loginId))  // 조건에 만족하는 값들을 가져옴
                .findFirst();  //그 값들 중에서 먼저 나온 값을 반환함 뒤에 애들은 무시함.
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());  // store의 Map<Long, Member>의 Member가(key,value중 value값) List로 반환됨
    }

    public void clearStore() {
        store.clear();
    }

}
