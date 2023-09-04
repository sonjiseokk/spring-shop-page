package toy.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import toy.shop.entity.dto.AuthorDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"authorName","nation","authorIntro"})
public class Author extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "author_id")
    private Long id;

    private String authorName;
    @Enumerated(EnumType.STRING)
    private AuthorNation nation;
    private String authorIntro;

    public Author(final String authorName, final AuthorNation nation, final String authorIntro) {
        this.authorName = authorName;
        this.nation = nation;
        this.authorIntro = authorIntro;
    }

    public void setNation(final AuthorNation nation) {
        this.nation = nation;
    }
    public void changeAuthorValues(AuthorDto dto){
        this.authorName = dto.getAuthorName();
        this.nation = dto.getNation();
        this.authorIntro = dto.getAuthorIntro();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(authorName, author.authorName) && nation == author.nation && Objects.equals(authorIntro, author.authorIntro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorName, nation, authorIntro);
    }
}
