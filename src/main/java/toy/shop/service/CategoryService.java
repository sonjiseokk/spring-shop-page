package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toy.shop.entity.Category;
import toy.shop.entity.dto.CategoryDto;
import toy.shop.repository.CategoryRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAllDto();
    }

    public Long save(Category category) {
//        Category category = categoryDto.toEntity();
        categoryRepository.save(category);
        return category.getCateCode();
    }

    public Category findById(final Long cateCode) {
        return categoryRepository.findById(cateCode);
    }
}
