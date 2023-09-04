package toy.shop.entity.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import toy.shop.entity.AuthorNation;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@EqualsAndHashCode
public class AuthorDto {
    private String authorName;
    @Enumerated(EnumType.STRING)
    private AuthorNation nation;
    private String authorIntro;

    public void setNation(final AuthorNation nation) {
        this.nation = nation;
    }

    public AuthorDto(final String authorName, final AuthorNation nation, final String authorIntro) {
        this.authorName = authorName;
        this.nation = nation;
        this.authorIntro = authorIntro;
    }
}
