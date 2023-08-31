package toy.shop.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionMemberDto {
    private String loginId;
    private String password;
    private String username;
    private String email;
    private String postnum;
    private String street;
    private String postetc;
    private Boolean adminChk;
    private LocalDateTime registerDate;
    private int money;
    private int point;

}
