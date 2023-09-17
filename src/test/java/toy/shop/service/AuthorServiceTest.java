package toy.shop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.Book;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.repository.AuthorRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static toy.shop.entity.AuthorNation.ETC;
import static toy.shop.entity.AuthorNation.KOREA;

@Transactional
@SpringBootTest
class AuthorServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorService authorService;
    @Autowired
    BookService bookService;

    @Test
    @DisplayName("author_enroll_test")
    void authorEnrollTest() throws Exception {
        //given
        AuthorDto authorDto = new AuthorDto("작가이름", KOREA, "설명");

        //when
        authorService.authorEnroll(authorDto);
        em.flush();
        em.clear();

        Author author = authorService.findByName(authorDto.getAuthorName()).get(0);

        //then
        assertThat(author.getAuthorName()).isEqualTo(authorDto.getAuthorName());
        assertThat(author.getNation()).isEqualTo(authorDto.getNation());
        assertThat(author.getAuthorIntro()).isEqualTo(authorDto.getAuthorIntro());
    }
    @Test
    @DisplayName("update")
    void update() throws Exception {
        //given
        Author author = new Author("이름", KOREA, "설명");
        AuthorDto authorDto = new AuthorDto("새로운이름", ETC,"새로운인트로");
        authorRepository.save(author);

        author.changeAuthorValues(authorDto);
        //when
        em.flush();
        em.clear();

        Author findAuthor = authorRepository.findById(author.getId()).get();
        //then
        assertThat(findAuthor.getAuthorName()).isEqualTo("새로운이름");
        assertThat(findAuthor.getNation()).isEqualTo(ETC);
        assertThat(findAuthor.getAuthorIntro()).isNotEqualTo("설명");
    }

    @Test
    @DisplayName("delete")
    void delete() throws Exception {
        //given
        AuthorDto authorDto = new AuthorDto("작가이름", KOREA,"작가설명");
        Long saveId = authorService.authorEnroll(authorDto);

        //when
        authorService.authorDelete(saveId);
        em.flush();
        em.clear();

        Optional<Author> optionalAuthor = authorService.findById(saveId);

        //then
        assertThat(optionalAuthor).isEmpty();
    }
    @Test
    @DisplayName("cant_delete_because_related_goods")
    void cantDeleteBecauseRelatedGoods() throws Exception {
        //given
        AuthorDto authorDto = new AuthorDto("작가이름", KOREA,"작가설명");
        Long savedAuthorId = authorService.authorEnroll(authorDto);

        Book book = new Book("책이름", LocalDate.now(), "퍼블리셔", 20000, 100, 0.1, "인트로", "컨텐츠");
        book.setAuthor(authorService.findById(savedAuthorId).get());

        bookService.bookEnroll(book);
        //when

        try {
            authorService.authorDelete(savedAuthorId);
            System.out.println("통과");
        } catch (Exception e) {
            System.out.println("실패");
        }
    }

}