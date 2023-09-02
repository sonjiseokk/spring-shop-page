package toy.shop.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Notice {
    @Id @GeneratedValue
    @Column(name = "page_id")
    private int pageNum;

    /* 페이지 표시 개수 */
    private int amount;

    /* 페이지 skip */
    private int skip;

    /* 검색 타입 */
    private String type;

    /* 검색 키워드 */
    private String keyword;

    /* Criteria 생성자 */
    public Notice(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.skip = (pageNum -1) * amount;
    }

    /* Criteria 기본 생성자 */
    public Notice(){
        this(1,10);
    }

    /* 검색 타입 데이터 배열 변환 */
    public String[] getTypeArr() {
        return type == null? new String[] {}:type.split("");
    }

    @Override
    public String toString() {
        return "Notice{" +
                "pageNum=" + pageNum +
                ", amount=" + amount +
                ", skip=" + skip +
                ", type='" + type + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
