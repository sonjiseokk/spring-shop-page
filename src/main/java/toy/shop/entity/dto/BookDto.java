package toy.shop.entity.dto;

import lombok.Getter;
import toy.shop.entity.Author;
import toy.shop.entity.Category;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Getter
public class BookDto {

    private String bookName;
    private Long authorId;
    private String publeYear;
    private String publisher;
    private Long cateCode;
    private int bookPrice;
    private int bookStock;
    private double bookDiscount;
    private String bookIntro;
    private String bookContents;

    public BookDto(final String bookName, final Long authorId, final String publeYear, final String publisher, final Long cateCode, final int bookPrice, final int bookStock, final double bookDiscount, final String bookIntro, final String bookContents) {
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
