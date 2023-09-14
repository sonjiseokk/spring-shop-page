package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.AuthorNation;
import toy.shop.entity.Book;
import toy.shop.entity.dto.PageDto;
import toy.shop.repository.AuthorRepository;
import toy.shop.repository.query.AuthorRepositoryQuery;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorRepositoryQuery authorRepositoryQuery;
    public void authorEnroll(Author author){
        authorRepository.save(author);
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public void update(Long id, final String authorName, final AuthorNation nation, final String authorIntro) {
        Optional<Author> authorById = findById(id);
        if (authorById.isEmpty()) {
            return;
        }
        Author author = authorById.get();
        author.changeAuthorValues(authorName,nation,authorIntro);
    }

    public Page<Author> authorList(PageDto pageDto){
        return authorRepositoryQuery.authorGetList(pageDto);
    }
    @Transactional
    public void authorDelete(Long id) throws Exception{
        Optional<Author> authorById = findById(id);
        if (authorById.isEmpty()) {
            return;
        }
        Author author = authorById.get();
        authorRepository.delete(author);
    }
}
