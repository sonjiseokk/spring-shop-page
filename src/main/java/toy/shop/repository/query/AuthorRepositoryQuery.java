package toy.shop.repository.query;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.entity.dto.QAuthorDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static toy.shop.entity.QAuthor.author;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryQuery{
    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;

    public Page<AuthorDto> authorGetList(PageDto pageDto) {
        Pageable pageable = pageDto.getPageable();
        QueryResults<AuthorDto> results =
                queryFactory.select(new QAuthorDto(
                                author.id,
                                author.authorName,
                                author.nation,
                                author.authorIntro,
                                author.createdDate,
                                author.lastModifiedDate
                        ))
                        .from(author)
                        .where(keywordCheck(pageDto.getKeyword()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

        List<AuthorDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate keywordCheck(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return author.authorName.likeIgnoreCase("%" + keyword + "%");
        }
        return null;
    }
}
