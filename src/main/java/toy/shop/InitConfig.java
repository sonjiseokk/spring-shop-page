package toy.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Member;
import toy.shop.service.MemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitMemberService initMemberService;
    @PostConstruct
    public void init() {
        initMemberService.init();
    }
    @Component
    static class InitMemberService {
        @Autowired
        MemberService memberService;
        @PersistenceContext
        EntityManager em;
        @Transactional
        public void init() {
            Member member = new Member("test", "test", "test", "test@gmail.com", "13480", "경기 성남시 분당구 대왕판교로 477 (판교동),", "판교판교",false);
            Member admin = new Member("admin", "admin", "admin", "admin@gmail.com", "12345", "경기 성남시 분당구 대왕판교로 477 (판교동),", "판교판교어드민",true);
            memberService.memberJoin(member);
            memberService.memberJoin(admin);
            em.flush();
            em.clear();
        }
    }
}
