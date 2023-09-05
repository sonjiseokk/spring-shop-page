package toy.shop.entity;

import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
public class Book extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    private LocalDateTime publeYear;
    private String publisher;
    private String cateCode;
    private int bookPrice;
    private int bookStock;
    private Integer bookDiscount;
    private String bookIntro;
    private String bookContents;

}

