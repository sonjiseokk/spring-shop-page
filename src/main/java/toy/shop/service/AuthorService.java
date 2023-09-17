package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shop.entity.Author;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public Long authorEnroll(AuthorDto authorDto){
        Author author = authorDto.toEntity();
        authorRepository.save(author);
        return author.getId();
    }

    public Optional<AuthorDto> findByIdReturnDto(Long id) {
        Author author = authorRepository.findById(id).get();
        return Optional.ofNullable(AuthorDto.builder()
                .id(author.getId())
                .authorName(author.getAuthorName())
                .nation(author.getNation())
                .authorIntro(author.getAuthorIntro())
                .createdDate(author.getCreatedDate())
                .lastModifiedDate(author.getLastModifiedDate())
                .build());
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
    public List<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Transactional
    public void update(final AuthorDto authorDto,Long id) {
        Optional<Author> authorById = findById(id);
        if (authorById.isEmpty()) {
            return;
        }
        Author author = authorById.get();
        author.changeAuthorValues(authorDto);
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
