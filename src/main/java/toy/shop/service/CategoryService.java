package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toy.shop.entity.Category;
import toy.shop.entity.dto.CategoryDto;
import toy.shop.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> search(String value) {
        List<Category> categories;
        if (value != null) {
            categories = categoryRepository.findAllWithCond();
        } else {
            categories =  categoryRepository.findAll();
        }

        return categories.stream()
                .map(m-> new CategoryDto(
                        m.getTier(),
                        m.getCateName(),
                        m.getCateParent()
                ))
                .collect(Collectors.toList());
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
