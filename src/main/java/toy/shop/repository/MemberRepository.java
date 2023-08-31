package toy.shop.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    private final PasswordEncoder pwEncoder;

    @Transactional
    public void save(Member member){
        // 스프링 시큐리티
        String nonSecurityPw = member.getPassword();
        String encodePw = pwEncoder.encode(nonSecurityPw);
        log.info("MemberContoller -> encodePw = {}",encodePw);
        member.changePw(encodePw);
        log.info("memberRepository -> member.getPw ={}",member.getPassword());

        em.persist(member);
    }
    public List<Member> findByLoginId(String loginId){
        return em.createQuery("select m from Member m where m.loginId = :loginId",Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    public Member findById(Long id) {
        return em.find(Member.class,id);
    }

}
