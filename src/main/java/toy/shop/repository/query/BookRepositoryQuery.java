package toy.shop.repository.query;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import toy.shop.entity.dto.GoodsDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.entity.dto.QGoodsDto;

import javax.persistence.EntityManager;
import java.util.List;

import static toy.shop.entity.QBook.book;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BookRepositoryQuery {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Page<GoodsDto> findAllWithCond(PageDto pageDto) {
        Pageable pageable = pageDto.getPageable();
        QueryResults<GoodsDto> results = queryFactory.select(new QGoodsDto(
                        book.id,
                        book.bookName,
                        book.publeYear,
                        book.publisher ,
                        book.bookPrice ,
                        book.bookStock ,
                        book.bookDiscount,
                        book.bookIntro ,
                        book.bookContents,
                        book.author.id,
                        book.author.authorName,
                        book.author.nation,
                        book.author.authorIntro,
                        book.author.createdDate,
                        book.author.lastModifiedDate,
                        book.category.cateCode,
                        book.category.tier,
                        book.category.cateName,
                        book.category.cateParent
                ))
                .from(book)
                .where(keywordCheck(pageDto.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<GoodsDto> content = results.getResults();
        log.info("content in query book = {}",content);
        long total = results.getTotal();
        log.info("query total in query book = {}",total);

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
