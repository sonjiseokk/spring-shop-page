package toy.shop.entity;

import lombok.*;
import toy.shop.entity.dto.AuthorDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    @OneToMany(mappedBy = "author")
    private List<Book> bookList = new ArrayList<>();

    @Builder
    public Author(final String authorName, final AuthorNation nation, final String authorIntro) {
        this.authorName = authorName;
        this.nation = nation;
        this.authorIntro = authorIntro;
    }

    public void setNation(final AuthorNation nation) {
        this.nation = nation;
    }
    public void changeAuthorValues(AuthorDto authorDto){
        this.authorName = authorDto.getAuthorName();
        this.nation = authorDto.getNation();
        this.authorIntro = authorDto.getAuthorIntro();
    }

    public AuthorDto toDto(){
        return AuthorDto.builder()
                .authorName(this.authorName)
                .nation(this.nation)
                .authorIntro(this.authorIntro)
                .build();
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
