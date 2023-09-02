package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toy.shop.entity.Author;
import toy.shop.repository.AuthorRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public void authorEnroll(Author author){
        authorRepository.save(author);
    }
}
