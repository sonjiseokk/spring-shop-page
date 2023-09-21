package toy.shop.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import toy.shop.entity.Category;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Setter
public class CategoryDto {
    private Long cateCode;
    private int tier;
    private String cateName;
    private Long cateParent;

    public CategoryDto() {
    }

    public CategoryDto(final int tier, final String cateName, final Long cateParent) {
        this.tier = tier;
        this.cateName = cateName;
        this.cateParent = cateParent;
    }

    @QueryProjection
    public CategoryDto(final Long cateCode, final int tier, final String cateName, final Long cateParent) {
        this.cateCode = cateCode;
        this.tier = tier;
        this.cateName = cateName;
        this.cateParent = cateParent;
    }

    public List<CategoryDto> entityToDto(List<Category> categories) {
        ArrayList<CategoryDto> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(new CategoryDto(category.getTier(), category.getCateName(), category.getCateParent()));
        }
        return list;

    }

    public Category toEntity() {
        return Category.builder()
                .tier(this.tier)
                .cateName(this.cateName)
                .cateParent(this.cateParent)
                .build();
    }

}
