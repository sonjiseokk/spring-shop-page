package toy.shop.entity.dto;

import lombok.Data;
import toy.shop.entity.Member;

import java.time.LocalDateTime;

@Data
public class SessionMemberDto {
    private String loginId;
    private String password;
    private String username;
    private String email;
    private Boolean adminChk;
    private int money;
    private int point;

    public void convertToSessionMemberDto(Member member) {
        this.email = (member.getEmail());
        this.loginId = (member.getLoginId());
        this.password = (member.getPassword());
        this.username = (member.getUsername());
        this.adminChk = (member.getAdminChk());
        this.money = (member.getMoney());
        this.point = (member.getPoint());
    }

}
