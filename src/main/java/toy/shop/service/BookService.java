package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.Book;
import toy.shop.entity.Category;
import toy.shop.entity.dto.BookDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.repository.BookRepository;
import toy.shop.repository.query.BookRepositoryQuery;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookRepositoryQuery bookRepositoryQuery;

    public void bookEnroll(Book book) {
        bookRepository.save(book);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Page<Book> pagingAllBook(PageDto pageDto) {
        return bookRepositoryQuery.findAllWithCond(pageDto);
    }

    @Transactional
    public void changeValues(final Long id, final BookDto dto, final Author author, final Category category, final LocalDate publeYear) {
        Book orgBook = findBookById(id);
        orgBook.changeDtoValue(dto, author, category, publeYear);
    }

//    public void update(final Book orgBook) {
//        bookRepositoryQuery.update(orgBook);
//    }
}
