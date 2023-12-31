package toy.shop.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Book;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookRepository {
    private final EntityManager em;

    @Transactional
    public void save(Book book) {
        em.persist(book);
    }

    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    public void delete(Book book) {
        em.remove(book);
    }
}
