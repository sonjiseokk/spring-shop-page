package toy.shop.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import toy.shop.entity.AuthorNation;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@ToString
public class GoodsDto {
    /*
        책
    */
    private Long bookId;
    private String bookName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publeYear;
    private String publisher;
    private int bookPrice;
    private int bookStock;
    private double bookDiscount;
    private String bookIntro;
    private String bookContents;

    /*
        작가
    */
    private Long authorId;
    private String authorName;
    @Enumerated(EnumType.STRING)
    private AuthorNation nation;
    private String authorIntro;
    private LocalDate createdDate; // BaseEntity의 createdDate를 포함
    private LocalDate lastModifiedDate; // BaseEntity의 lastModifiedDate를 포함

    /*
        카테고리
     */
    private Long cateCode;
    private int tier;
    private String cateName;
    private Long cateParent;

//    public GoodsDto(BookDto bookDto, AuthorDto authorDto, CategoryDto categoryDto) {
//        this.bookId = bookDto.getId();
//        this.bookName = bookDto.getBookName();
//        this.publeYear = bookDto.getPubleYear();
//        this.publisher = bookDto.getPublisher();
//        this.bookPrice = bookDto.getBookPrice();
//        this.bookStock = bookDto.getBookStock();
//        this.bookDiscount = bookDto.getBookDiscount();
//        this.bookIntro = bookDto.getBookIntro();
//        this.bookContents = bookDto.getBookContents();
//        this.authorId = authorDto.getId();
//        this.authorName = authorDto.getAuthorName();
//        this.nation = authorDto.getNation();
//        this.authorIntro = authorDto.getAuthorIntro();
//        this.createdDate = authorDto.getCreatedDate();
//        this.lastModifiedDate = authorDto.getLastModifiedDate();
//        this.cateCode = categoryDto.getCateCode();
//        this.tier = categoryDto.getTier();
//        this.cateName = categoryDto.getCateName();
//        this.cateParent = categoryDto.getCateParent();
//    }

    @QueryProjection
    public GoodsDto(final Long bookId, final String bookName, final LocalDate publeYear, final String publisher, final int bookPrice, final int bookStock, final double bookDiscount, final String bookIntro, final String bookContents, final Long authorId, final String authorName, final AuthorNation nation, final String authorIntro, final LocalDate createdDate, final LocalDate lastModifiedDate, final Long cateCode, final int tier, final String cateName, final Long cateParent) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.publeYear = publeYear;
        this.publisher = publisher;
        this.bookPrice = bookPrice;
        this.bookStock = bookStock;
        this.bookDiscount = bookDiscount;
        this.bookIntro = bookIntro;
        this.bookContents = bookContents;
        this.authorId = authorId;
        this.authorName = authorName;
        this.nation = nation;
        this.authorIntro = authorIntro;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.cateCode = cateCode;
        this.tier = tier;
        this.cateName = cateName;
        this.cateParent = cateParent;
    }
}
