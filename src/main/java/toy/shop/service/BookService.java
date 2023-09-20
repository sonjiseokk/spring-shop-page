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
import toy.shop.entity.dto.GoodsDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.repository.BookRepository;
import toy.shop.repository.query.BookRepositoryQuery;

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

    public Page<GoodsDto> pagingAllBook(PageDto pageDto) {
        return bookRepositoryQuery.findAllWithCond(pageDto);
    }

    @Transactional
    public void changeValues(final Long id, final BookDto dto, final Author author, final Category category) {
        Book orgBook = findBookById(id);
        orgBook.changeDtoValue(dto, author, category);
    }

    @Transactional
    public void goodsDelete(Long id) throws Exception{
        Book book = bookRepository.findById(id);
        bookRepository.delete(book);
    }

    public Book setBook(BookDto dto, final Author relatedAuthor, final Category relatedCategory) {
        Book book = new Book(dto.getBookName(),dto.getPubleYear(),dto.getPublisher(),dto.getBookPrice(), dto.getBookStock(), dto.getBookDiscount(),dto.getBookIntro(),dto.getBookContents());
        book.setAuthor(relatedAuthor);
        book.setCategory(relatedCategory);
        return book;
    }


}
