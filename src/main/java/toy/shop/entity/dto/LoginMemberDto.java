package toy.shop.entity.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginMemberDto {
    private String loginId;
    private String password;

    public LoginMemberDto(final String loginId, final String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
