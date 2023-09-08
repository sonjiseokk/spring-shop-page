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
import toy.shop.entity.Author;
import toy.shop.entity.Book;
import toy.shop.entity.QBook;
import toy.shop.entity.dto.PageDto;

import javax.persistence.EntityManager;
import java.util.List;

import static toy.shop.entity.QAuthor.author;
import static toy.shop.entity.QBook.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookRepositoryQuery {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Page<Book> findAllWithCond(PageDto pageDto) {
        Pageable pageable = pageDto.getPageable();
        QueryResults<Book> results = queryFactory.select(book)
                .from(book)
                .where(keywordCheck(pageDto.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Book> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate keywordCheck(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return book.bookName.likeIgnoreCase("%" + keyword + "%");
        }
        return null;
    }
}
