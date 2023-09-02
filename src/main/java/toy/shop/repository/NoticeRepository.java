package toy.shop.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import toy.shop.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
public class NoticeRepository {
    @PersistenceContext
    EntityManager em;

//    public List<Author> authorGetList(){
//        em.createQuery("select a from Author a " +
//    }
}
