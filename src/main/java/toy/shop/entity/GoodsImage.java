package toy.shop.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class GoodsImage {
    @Id
    private String uuid;
    private String fileName;
    private String uploadPath;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;



}
