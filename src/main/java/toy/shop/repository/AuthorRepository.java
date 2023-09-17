package toy.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
