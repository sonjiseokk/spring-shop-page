package toy.shop.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberTest {
    @Autowired
    EntityManager em;
    @Test
    @DisplayName("create_table_test")
    void createTableTest() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("안녕");
        //when
        em.persist(member);
        em.flush();
        em.clear();

        //then
//        assertThat()
    }

}