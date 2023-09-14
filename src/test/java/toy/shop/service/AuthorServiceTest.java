package toy.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.Book;
import toy.shop.entity.Category;
import toy.shop.entity.dto.BookDto;
import toy.shop.repository.AuthorRepository;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("update")
    void update() throws Exception {
        //given
        Author author = new Author("이름", KOREA, "설명");
        authorRepository.save(author);

        authorService.update(author.getId(),"새로운이름", ETC,"새로운인트로");
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
        Author author = new Author("작가이름", KOREA,"작가설명");
        authorService.authorEnroll(author);
        em.flush();
        em.clear();
        //when
        authorService.authorDelete(author.getId());

        Optional<Author> optionalAuthor = authorService.findById(author.getId());

        //then
        assertThat(optionalAuthor).isEmpty();
    }
    @Test
    @DisplayName("cant_delete_because_related_goods")
    void cantDeleteBecauseRelatedGoods() throws Exception {
        //given
        Author author = new Author("작가이름", KOREA,"작가설명");
        Book book = new Book("책이름", LocalDate.now(), "퍼블리셔", 20000, 100, 0.1, "인트로", "컨텐츠");
        book.setAuthor(author);

        authorService.authorEnroll(author);
        bookService.bookEnroll(book);
        //when

        try {
            authorService.authorDelete(author.getId());
            System.out.println("통과");
        } catch (Exception e) {
            System.out.println("실패");
        }


        //then
    }

}