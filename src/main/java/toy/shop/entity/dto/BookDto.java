package toy.shop.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import toy.shop.entity.web.AttachImage;

import javax.persistence.Embedded;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDto {
    private Long id;
    private String bookName;
    private Long authorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publeYear;
    private String publisher;
    private Long cateCode;
    private int bookPrice;
    private int bookStock;
    private double bookDiscount;
    private String bookIntro;
    private String bookContents;
    @Embedded
    private AttachImage attachImage;
    public BookDto(final String bookName, final Long authorId, final LocalDate publeYear, final String publisher, final Long cateCode, final int bookPrice, final int bookStock, final double bookDiscount, final String bookIntro, final String bookContents) {
        this.bookName = bookName;
        this.authorId = authorId;
        this.publisher = publisher;
        this.cateCode = cateCode;
        this.bookPrice = bookPrice;
        this.bookStock = bookStock;
        this.bookDiscount = bookDiscount;
        this.bookIntro = bookIntro;
        this.bookContents = bookContents;
        this.publeYear = publeYear;
    }

    @QueryProjection
    public BookDto(final Long id, final String bookName, final Long authorId, final LocalDate publeYear, final String publisher, final Long cateCode, final int bookPrice, final int bookStock, final double bookDiscount, final String bookIntro, final String bookContents) {
        this.id = id;
        this.bookName = bookName;
        this.authorId = authorId;
        this.publeYear = publeYear;
        this.publisher = publisher;
        this.cateCode = cateCode;
        this.bookPrice = bookPrice;
        this.bookStock = bookStock;
        this.bookDiscount = bookDiscount;
        this.bookIntro = bookIntro;
        this.bookContents = bookContents;
    }

    public LocalDate convertStringToLocalDate(String value){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(value, formatter);
    }

}
