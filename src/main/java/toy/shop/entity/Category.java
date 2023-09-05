package toy.shop.entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private int tier;
    private String cateName;
    private String cateCode;
    private String cateParent;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


}
