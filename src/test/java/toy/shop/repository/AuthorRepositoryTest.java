package toy.shop.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.repository.query.AuthorRepositoryQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorRepositoryQuery authorRepositoryQuery;
    @Autowired EntityManager em;

    @Test
    @DisplayName("author_get_list")
    void authorGetList() throws Exception {
        //given
        Author author1 = new Author("이름1", AuthorNation.KOREA, "설명");
        Author author2 = new Author("이름2", AuthorNation.KOREA, "설명");
        Author author3 = new Author("이름3", AuthorNation.KOREA, "설명");
        Author author4 = new Author("름이4", AuthorNation.KOREA, "설명");
        Author author5 = new Author("름이5", AuthorNation.KOREA, "설명");
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        authorRepository.save(author4);
        authorRepository.save(author5);

        em.flush();
        em.clear();

//        //when
//        List<Author> authorList = authorRepositoryQuery.authorGetList(Pageable.unpaged()).getContent();
//        for (Author author : authorList) {
//            System.out.println("author = " + author);
//        }
//        //then
//        assertThat(authorList.size()).isEqualTo(3);
//        assertThat(authorList.get(0)).isEqualTo(author1);
    }

}