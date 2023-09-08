package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toy.shop.entity.Category;
import toy.shop.repository.CategoryRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> search(String value) {
        if (value != null) {
            return categoryRepository.findAllWithCond();
        } else {
            return categoryRepository.findAll();
        }
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category findById(final Long cateCode) {
        return categoryRepository.findById(cateCode);
    }
}
