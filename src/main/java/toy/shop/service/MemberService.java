package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toy.shop.entity.dto.LoginMemberDto;
import toy.shop.entity.Member;
import toy.shop.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder pwEncoder;

    public void memberJoin(final Member member){
        member.newMemberInit();
        Member securityMember = enableSecurity(member);
        memberRepository.save(securityMember);
    }

    public boolean idCanUseChk(final String loginId) {
        List<Member> result = memberRepository.findByLoginId(loginId);
        return result.size() == 0;
    }

    public Optional<Member> login(LoginMemberDto dto) {
        List<Member> searchToIds = memberRepository.findByLoginId(dto.getLoginId());
        if (searchToIds.size() != 1) {
            return Optional.empty();
        }

        Member findMember = searchToIds.get(0);
        if(pwEncoder.matches(dto.getPassword(), findMember.getPassword())) {        // 비밀번호 일치여부 판단
            return Optional.of(searchToIds.get(0));
        }
        return Optional.empty();
    }
    public Member enableSecurity(Member member){
        // 스프링 시큐리티
        String nonSecurityPw = member.getPassword();
        String encodePw = pwEncoder.encode(nonSecurityPw);
        member.changePw(encodePw);
        return member;
    }

}
