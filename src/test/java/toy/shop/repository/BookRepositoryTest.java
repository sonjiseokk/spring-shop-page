package toy.shop.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookRepositoryTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("save_test")
    void saveTest() throws Exception {
        //given
        Book book = new Book("책이름", LocalDate.now(),"출판사",20000,300,0.23,"책 소개","책 목차");
        bookRepository.save(book);
        em.flush();
        em.clear();
        //when
        Book findBook = bookRepository.findById(book.getId());

        //then
        assertThat(book.getPublisher()).isEqualTo(findBook.getPublisher());
        assertThat(book.getBookPrice()).isEqualTo(findBook.getBookPrice());
        assertThat(book.getBookDiscount()).isEqualTo(findBook.getBookDiscount());
        assertThat(book.getBookIntro()).isEqualTo(findBook.getBookIntro());
        assertThat(book.getBookContents()).isEqualTo(findBook.getBookContents());

    }

}