package toy.shop.entity.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shop.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryDto {
    private Long cateCode;
    private int tier;
    private String cateName;
    private Long cateParent;

    public CategoryDto() {
    }

    public CategoryDto(final Long cateCode, final int tier, final String cateName, final Long cateParent) {
        this.cateCode = cateCode;
        this.tier = tier;
        this.cateName = cateName;
        this.cateParent = cateParent;
    }

    public List<CategoryDto> entityToDto(List<Category> categories) {
        ArrayList<CategoryDto> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(new CategoryDto(category.getCateCode(),category.getTier(), category.getCateName(), category.getCateParent()));
        }
        return list;

    }
}
