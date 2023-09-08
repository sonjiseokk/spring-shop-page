package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toy.shop.entity.Book;
import toy.shop.entity.dto.BookDto;
import toy.shop.repository.BookRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void bookEnroll(Book book) {
        bookRepository.save(book);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void dtoToBook(final BookDto dto) {

    }
}
