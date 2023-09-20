package toy.shop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Category;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("search_test")
    void searchTest() throws Exception {
        //given
        Category category1 = new Category(1,"국내",null);
        categoryService.save(category1);
        Category category2 = new Category(2,"소설",category1.getCateCode());
        categoryService.save(category2);

        em.flush();
        em.clear();

        //when
        List<Category> categoryDtos = categoryService.search(null);
//        for (CategoryDto categoryDto : categoryDtos) {
//            System.out.println("categoryDto = " + categoryDto);
//        }
//
//        //then
//        assertThat(categoryDtos.get(0).getTier()).isEqualTo(1);
//        assertThat(categoryDtos.size()).isEqualTo(2);
//        assertThat(categoryDtos.get(1).getCateParent()).isEqualTo(category1.getCateCode());
    }

}