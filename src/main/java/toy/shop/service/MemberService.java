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

    public void memberJoin(final Member member){
        member.newMemberInit();
        memberRepository.save(member);
    }

    public boolean idCanUseChk(final String loginId) {
        List<Member> result = memberRepository.findByLoginId(loginId);
        return result.size() == 0;
    }

    public Optional<Member> login(LoginMemberDto dto) {
        List<Member> searchToIds = memberRepository.findByLoginId(dto.getLoginId());
        log.info("dto.getLoginId ={}", dto.getLoginId());
        for (Member searchToId : searchToIds) {
            log.info("searchToId = {}", searchToId);
        }
        if (searchToIds.size() != 1) {
            return Optional.empty();
        }

        return Optional.of(searchToIds.get(0));
    }

}
