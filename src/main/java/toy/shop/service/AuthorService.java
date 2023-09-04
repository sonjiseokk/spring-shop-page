package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toy.shop.entity.Author;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.repository.AuthorRepository;
import toy.shop.repository.query.AuthorRepositoryQuery;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorRepositoryQuery authorRepositoryQuery;
    public void authorEnroll(Author author){
        authorRepository.save(author);
    }

    public Author findById(Long id) {
        return authorRepository.findById(id);
    }

    public void update(Author author) {
        authorRepository.update(author);
    }

    public Page<Author> authorList(PageDto pageDto){
        return authorRepositoryQuery.authorGetList(pageDto);
    }
}
