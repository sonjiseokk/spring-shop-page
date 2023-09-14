package toy.shop.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import toy.shop.entity.Author;
import toy.shop.entity.QAuthor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static toy.shop.entity.QAuthor.*;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorRepository {
    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;
    @Transactional
    public void save(Author author) {
        em.persist(author);
    }

    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    public List<Author> findByName(String authorName){
        return em.createQuery("select a from Author a where a.authorName = :authorName", Author.class)
                .setParameter("authorName", authorName)
                .getResultList();
    }

    public void delete(Author author) {
        em.remove(author);
    }

}
