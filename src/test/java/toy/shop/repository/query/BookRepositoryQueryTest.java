package toy.shop.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.Book;
import toy.shop.entity.Category;
import toy.shop.entity.dto.GoodsDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.repository.AuthorRepository;
import toy.shop.repository.BookRepository;
import toy.shop.repository.CategoryRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@SpringBootTest
@Transactional
class BookRepositoryQueryTest {
    @Autowired
    BookRepositoryQuery bookRepositoryQuery;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    EntityManager em;
    @Test
    @DisplayName("paging_search_test")
    void pagingSearchTest() throws Exception {
        //given
//        Author author = new Author("이름", AuthorNation.KOREA, "설명");
//        authorRepository.save(author);
//
//        Book book = new Book("상품", LocalDate.now(), "퍼블리셔", 2000 , 10, 0.1, "책소개", "작가소개");
//        book.setAuthor(author);
//
//        bookRepository.save(book);
        for (int i = 0; i < 10; i++) {
            Author author1 = new Author("이름" + i, AuthorNation.KOREA, "설명");
//                Category category = new Category(1, "국내", null);
//                categoryRepository.save(category);
            Book book = new Book("상품"+i, LocalDate.now(), "퍼블리셔", 2000 * i, 10 * i, 0.1, "책소개", "작가소개");
            book.setAuthor(author1);
//                book.setCategory(category);
            bookRepository.save(book);
            authorRepository.save(author1);
        }
        Category category = new Category(1, "국내", null);
        em.persist(category);
        Category category1 = new Category(2, "소설", category.getCateCode());
        em.persist(category1);
        Category category2 = new Category(3, "한국소설", category1.getCateCode());
        em.persist(category2);
        Category category01 = new Category(1, "해외", null);
        em.persist(category01);
        Category category11 = new Category(2, "경제/경영", category01.getCateCode());
        em.persist(category11);
        Category category12 = new Category(3, "경영일반", category11.getCateCode());
        em.persist(category12);

        Pageable page = PageRequest.of(0, 10);
        //when
        Page<GoodsDto> all = bookRepositoryQuery.findAllWithCond(new PageDto(page, ""));

        for (GoodsDto dto : all.getContent()) {
            System.out.println("dto = " + dto);
        }
        //then
    }
}