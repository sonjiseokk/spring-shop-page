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
        Author author = initAuthor("작가이름",AuthorNation.KOREA,"인트로");
        Category category = initCategory(1, "국내", null);

        BookDto dto = getBookDto(author, category);
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

    @Test
    @DisplayName("change_dto_values")
    void changeDtoValues() throws Exception {
        //given
        // dto가 주어지고 (컨트롤러)
        Author author = initAuthor("작가이름2",AuthorNation.ETC,"인트로");
        Category category = initCategory(1, "해외", null);
        BookDto dto = getBookDto(author,category);
        LocalDate date = dto.convertStringToLocalDate(dto.getPubleYear());

        Book book = new Book("기존이름", date, "기존퍼블리셔", 20000, 100, 0.1, "기존인트로", "기존컨텐츠");
        book.setCategory(null);
        book.setAuthor(null);
        //when
        bookService.bookEnroll(book);
        bookService.changeValues(book.getId(), dto, author, category, date);

        em.flush();
        em.clear();
        Book findBook = bookService.findBookById(book.getId());
        //then
        assertThat(findBook.getAuthor().getAuthorName()).isEqualTo(author.getAuthorName());
        assertThat(findBook.getCategory().getCateCode()).isEqualTo(category.getCateCode());
        assertThat(findBook.getPubleYear()).isEqualTo(date);

        assertThat(findBook.getBookName()).isNotEqualTo("기존이름");
    }
    private Author initAuthor(final String authorName,AuthorNation nation,String intro) {
        Author author = new Author(authorName, nation,intro);
        authorService.authorEnroll(author);
        return author;
    }
    private Category initCategory(int tier,String cateName,Long cateParent){
        Category category = new Category(tier,cateName,cateParent);
        categoryService.save(category);
        return category;
    }
    private static BookDto getBookDto(final Author author, final Category category) {
        return new BookDto("새로운 북이름", author.getId(), "2023-09-08", "새로운 퍼블리셔", category.getCateCode(), 10000, 100, 0.2, "새로운 인트로", "새로운 콘텐츠");
    }
}