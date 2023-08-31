package toy.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Member;
import toy.shop.entity.dto.LoginMemberDto;
import toy.shop.repository.MemberRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    void memberJoin() {
        // given
        Member member = new Member("aaa11", "12345");
        memberService.memberJoin(member);

        Member findMember = memberRepository.findById(member.getId());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("login")
    void login() throws Exception {
        //given
        LoginMemberDto dto = new LoginMemberDto("aaa","aaa11");

        Member registMember = new Member(dto.getLoginId(), dto.getPassword());
        memberService.memberJoin(registMember);
        em.flush();
        em.clear();

        //when
        Member loginMember = memberService.login(dto).orElseThrow();

        //then
        assertThat(loginMember.getLoginId()).isEqualTo(dto.getLoginId());
        assertThat(loginMember.getPassword()).isEqualTo(dto.getPassword());
    }
}