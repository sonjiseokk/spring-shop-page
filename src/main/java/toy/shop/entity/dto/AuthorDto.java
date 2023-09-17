package toy.shop.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.BaseEntity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorDto extends BaseEntity {
    private Long id;
    private String authorName;
    @Enumerated(EnumType.STRING)
    private AuthorNation nation;
    private String authorIntro;
    private LocalDate createdDate; // BaseEntity의 createdDate를 포함
    private LocalDate lastModifiedDate; // BaseEntity의 lastModifiedDate를 포함


    public void setNation(final AuthorNation nation) {
        this.nation = nation;
    }

    @QueryProjection
    @Builder
    public AuthorDto(final Long id, final String authorName, final AuthorNation nation, final String authorIntro, final LocalDate createdDate, final LocalDate lastModifiedDate) {
        this.id = id;
        this.authorName = authorName;
        this.nation = nation;
        this.authorIntro = authorIntro;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    @Builder
    public AuthorDto(final String authorName, final AuthorNation nation, final String authorIntro) {
        this.authorName = authorName;
        this.nation = nation;
        this.authorIntro = authorIntro;
    }


    public Author toEntity(){
        return Author.builder()
                .authorName(this.authorName)
                .nation(this.nation)
                .authorIntro(this.authorIntro)
                .build();
    }



}
