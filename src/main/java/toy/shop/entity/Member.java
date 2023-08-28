package toy.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@ToString(of = {"loginId","password","username","email","address1","address2","address3"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String username;
    private String email;
    private String address1;
    private String address2;
    private String address3;
    private Boolean adminChk;
    private LocalDateTime registerDate;
    private int money;
    private int point;

}
