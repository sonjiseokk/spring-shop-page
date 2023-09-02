package toy.shop.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Embeddable;

public enum AuthorNation {
    KOREA,ETC;
    @JsonCreator
    public static AuthorNation from(String s) {
        return AuthorNation.valueOf(s.toUpperCase());
    }
}
