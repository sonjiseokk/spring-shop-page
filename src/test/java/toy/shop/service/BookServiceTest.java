package toy.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.Book;
import toy.shop.entity.Category;
import toy.shop.entity.dto.BookDto;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("bookdto_enroll_test")
    @Commit
    void bookEnrollTest() throws Exception {
        //given
        Author author = new Author("작가이름", AuthorNation.KOREA,"작가소개");
        authorService.authorEnroll(author);
        Category category = new Category(1,"한국",null);
        categoryService.save(category);

        BookDto dto = new BookDto("북이름", author.getId(), "2023-09-08", "퍼블리셔", 1L,10000, 100, 0.2, "인트로", "콘텐츠");
        em.flush();
        em.clear();
        //when
        Author relatedAuthor = authorService.findById(dto.getAuthorId());

        LocalDate date = dto.convertStringToLocalDate(dto.getPubleYear());
        Book book = new Book(dto.getBookName(),date,dto.getPublisher(),dto.getBookPrice(), dto.getBookPrice(), dto.getBookDiscount(),dto.getBookIntro(),dto.getBookContents());
        book.setAuthor(relatedAuthor);

        bookService.bookEnroll(book);

        em.flush();
        em.clear();

        Book findBook = bookService.findBookById(book.getId());
        //then
        assertThat(findBook.getAuthor().getAuthorName()).isEqualTo(relatedAuthor.getAuthorName());
    }

}