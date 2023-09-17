package toy.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long cateCode;
    private int tier;
    private String cateName;
    private Long cateParent;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    @Builder
    public Category(final int tier, final String cateName, final Long cateParent) {
        this.tier = tier;
        this.cateName = cateName;
        this.cateParent = cateParent;
    }
}
