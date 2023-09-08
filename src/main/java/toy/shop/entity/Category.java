package toy.shop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long cateCode;
    private int tier;
    private String cateName;
    private Long cateParent;
    @OneToMany(mappedBy = "category")
    private List<Book> books = new ArrayList<>();

    public Category(final int tier, final String cateName, final Long cateParent) {
        this.tier = tier;
        this.cateName = cateName;
        this.cateParent = cateParent;
    }
}
