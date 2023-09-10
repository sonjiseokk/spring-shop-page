package toy.shop.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.repository.AuthorRepository;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static toy.shop.entity.AuthorNation.ETC;
import static toy.shop.entity.AuthorNation.KOREA;

@Transactional
@SpringBootTest
class AuthorServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorService authorService;
    @Test
    @DisplayName("update")
    void update() throws Exception {
        //given
        Author author = new Author("이름", KOREA, "설명");
        authorRepository.save(author);

        authorService.update(author.getId(),"새로운이름", ETC,"새로운인트로");
        //when
        em.flush();
        em.clear();

        Author findAuthor = authorRepository.findById(author.getId());
        //then
        assertThat(findAuthor.getAuthorName()).isEqualTo("새로운이름");
        assertThat(findAuthor.getNation()).isEqualTo(ETC);
        assertThat(findAuthor.getAuthorIntro()).isNotEqualTo("설명");
    }

}