package toy.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.shop.entity.Member;
import toy.shop.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void memberJoin(final Member member){
        memberRepository.save(member);
    }

    public boolean idCanUseChk(final String loginId) {
        List<Member> result = memberRepository.findByLoginId(loginId);
        if (result.size() != 0) {
            return false;
        } else {
            return true;
        }
    }

}
