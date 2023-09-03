package toy.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
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

    public Member(final String loginId, final String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public Member(final String loginId, final String password, final String username, final String email, final String postnum, final String street, final String postetc, final Boolean adminChk) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.postnum = postnum;
        this.street = street;
        this.postetc = postetc;
        this.adminChk = adminChk;
    }

    public void newMemberInit(){
        this.registerDate = LocalDateTime.now();
        this.money = 0;
        this.point = 0;
    }
    public void changePw(String password){
        this.password = password;
    }

}
