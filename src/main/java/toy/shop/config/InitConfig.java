package toy.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.Category;
import toy.shop.entity.Member;
import toy.shop.repository.AuthorRepository;
import toy.shop.service.MemberService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitMemberService initMemberService;
    @PostConstruct
    public void init() {
        initMemberService.init();
    }
    @Component
    static class InitMemberService {
        @Autowired
        MemberService memberService;
        @Autowired
        AuthorRepository authorRepository;
        @PersistenceContext
        EntityManager em;
        @Transactional
        public void init() {
            Member member = new Member("test", "test", "test", "test@gmail.com", "13480", "경기 성남시 분당구 대왕판교로 477 (판교동),", "판교판교",false);
            Member admin = new Member("admin", "admin", "admin", "admin@gmail.com", "12345", "경기 성남시 분당구 대왕판교로 477 (판교동),", "판교판교어드민",true);
            memberService.memberJoin(member);
            memberService.memberJoin(admin);

            for (int i = 0; i < 100; i++) {
                Author author1 = new Author("이름" + i, AuthorNation.KOREA, "설명");
                authorRepository.save(author1);

            }

            Category category = new Category(1, "국내", null);
            em.persist(category);
            Category category1 = new Category(2, "소설", category.getCateCode());
            em.persist(category1);
            Category category2 = new Category(3, "한국소설", category1.getCateCode());
            em.persist(category2);


            Category category01 = new Category(1, "해외", null);
            em.persist(category01);
            Category category11 = new Category(2, "경제/경영", category01.getCateCode());
            em.persist(category11);
            Category category12 = new Category(3, "경영일반", category11.getCateCode());
            em.persist(category12);


            em.flush();
            em.clear();
        }
    }
}
