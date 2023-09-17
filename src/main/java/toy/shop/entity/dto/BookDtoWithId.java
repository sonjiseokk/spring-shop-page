package toy.shop.entity.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public class BookDtoWithId extends BookDto{
    private Long id;

    @QueryProjection
    public BookDtoWithId(final Long id,final String bookName, final Long authorId, final LocalDate publeYear, final String publisher, final Long cateCode, final int bookPrice, final int bookStock, final double bookDiscount, final String bookIntro, final String bookContents) {
        super(bookName, authorId, publeYear, publisher, cateCode, bookPrice, bookStock, bookDiscount, bookIntro, bookContents);
        this.id = id;
    }
}
