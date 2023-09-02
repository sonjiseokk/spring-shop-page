package toy.shop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AuthorRepository {
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public void save(Author author) {
        em.persist(author);
    }

    public Author findById(Long id) {
        return em.find(Author.class, id);
    }

    public List<Author> findByName(String authorName){
        return em.createQuery("select a from Author a where a.authorName = :authorName", Author.class)
                .setParameter("authorName", authorName)
                .getResultList();
    }
}
