package toy.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Category;
import toy.shop.entity.QCategory;

import javax.persistence.EntityManager;
import java.util.List;

import static toy.shop.entity.QCategory.*;

@Repository
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Category> findAll(){
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    public List<Category> findAllWithCond() {
        return queryFactory
                .select(category)
                .from(category)
                .where()
                .fetch();
    }

    public void save(final Category category) {
        em.persist(category);
    }

    public Category findById(final Long cateCode) {
        return em.find(Category.class, cateCode);
    }

    public void delete(Category category) {
        em.remove(category);

    }
}
