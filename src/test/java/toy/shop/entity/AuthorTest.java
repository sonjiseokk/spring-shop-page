package toy.shop.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.repository.AuthorRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AuthorTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    EntityManager em;
    @Test
    @DisplayName("author_save")
    void authorSave() throws Exception {
        //given
        Author author = new Author("작가이름", AuthorNation.KOREA, "작가소개");
        authorRepository.save(author);

        em.flush();
        em.clear();

        //when
        Author findAuthor = authorRepository.findById(author.getId()).get();

        //then
        assertThat(author.getAuthorName()).isEqualTo(findAuthor.getAuthorName());
        assertThat(author.getNation()).isEqualTo(findAuthor.getNation());
        assertThat(author).isEqualTo(findAuthor);
    }
}