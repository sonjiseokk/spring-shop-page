package toy.shop.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Member;


import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional

public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    void save() {
        // given
        Member member = new Member("aaa11", "12345");
        memberRepository.save(member);
        em.flush();
        em.clear();

        // when
        List<Member> members = memberRepository.findByLoginId("aaa11");
        Member findMember = members.get(0);

        // then
        assertThat(member).isEqualTo(findMember);
    }



}