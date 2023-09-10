package toy.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shop.entity.dto.BookDto;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String bookName;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDate publeYear;
    private String publisher;
    private int bookPrice;
    private int bookStock;
    private double bookDiscount;
    private String bookIntro;
    private String bookContents;


    public Book(final String bookName, final LocalDate publeYear, final String publisher, final int bookPrice, final int bookStock, final double bookDiscount, final String bookIntro, final String bookContents) {
        this.bookName = bookName;
        this.publeYear = publeYear;
        this.publisher = publisher;
        this.bookPrice = bookPrice;
        this.bookStock = bookStock;
        this.bookDiscount = bookDiscount;
        this.bookIntro = bookIntro;
        this.bookContents = bookContents;
    }

    public void setAuthor(final Author author) {
        this.author = author;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void changeDtoValue(BookDto dto,Author author,Category category,LocalDate publeYear) {
        this.bookName = dto.getBookName();
        this.author = author;
        this.category = category;
        this.publeYear = publeYear;
        this.publisher = dto.getPublisher();
        this.bookPrice = dto.getBookPrice();
        this.bookStock = dto.getBookStock();
        this.bookDiscount = dto.getBookDiscount();
        this.bookIntro = dto.getBookIntro();
        this.bookContents = dto.getBookContents();
    }
}

