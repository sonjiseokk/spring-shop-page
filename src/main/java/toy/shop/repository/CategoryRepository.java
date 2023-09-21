package toy.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Category;
import toy.shop.entity.dto.CategoryDto;
import toy.shop.entity.dto.QCategoryDto;

import javax.persistence.EntityManager;
import java.util.List;

import static toy.shop.entity.QCategory.category;

@Repository
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<CategoryDto> findAllDto(){
        return queryFactory.select(new QCategoryDto(
                        category.cateCode,
                        category.tier,
                        category.cateName,
                        category.cateParent
                ))
                .from(category)
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
