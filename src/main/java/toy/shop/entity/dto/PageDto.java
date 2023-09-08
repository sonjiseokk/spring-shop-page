package toy.shop.entity.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import toy.shop.controller.PageRequest;

@Getter
@ToString
public class PageDto {
    private Pageable pageable;
    private String keyword;

    public PageDto(final Pageable pageable, final String keyword) {
        this.pageable = pageable;
        this.keyword = keyword;
    }
}
