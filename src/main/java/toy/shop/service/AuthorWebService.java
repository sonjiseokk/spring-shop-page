package toy.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import toy.shop.entity.dto.AuthorDto;
import toy.shop.entity.dto.PageDto;
import toy.shop.repository.query.AuthorRepositoryQuery;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorWebService {
    private final AuthorRepositoryQuery authorRepositoryQuery;
    public Page<AuthorDto> authorList(PageDto pageDto){
        return authorRepositoryQuery.authorGetList(pageDto);
    }
}
