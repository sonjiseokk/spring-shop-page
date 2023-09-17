package toy.shop.repository.query;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import toy.shop.entity.Book;
import toy.shop.entity.dto.BookDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.entity.dto.QBookDto;

import javax.persistence.EntityManager;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static toy.shop.entity.QBook.book;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookRepositoryQuery {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Page<BookDto> findAllWithCond(PageDto pageDto) {
        Pageable pageable = pageDto.getPageable();
        QueryResults<BookDto> results = queryFactory.select(new QBookDto(
                        book.bookName,
                        book.author.id,
                        book.publeYear,
                        book.publisher ,
                        book.category.cateCode,
                        book.bookPrice ,
                        book.bookStock ,
                        book.bookDiscount,
                        book.bookIntro ,
                        book.bookContents
                ))
                .from(book)
                .where(keywordCheck(pageDto.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<BookDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
//    public void update(final Book orgBook) {
//        queryFactory
//                .update(book)
//                .set(orgBook)
//    }

    private Predicate keywordCheck(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return book.bookName.likeIgnoreCase("%" + keyword + "%");
        }
        return null;
    }


}
