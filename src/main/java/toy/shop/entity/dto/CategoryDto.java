package toy.shop.entity.dto;

import lombok.Getter;

@Getter
public class CategoryDto {
    private int tier;
    private String cateName;
    private String cateParent;
}
