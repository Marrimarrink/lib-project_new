package ru.itgirl.libproject_new.service;

import ru.itgirl.libproject_new.dto.author.request.AuthorCreateDto;
import ru.itgirl.libproject_new.dto.author.response.AuthorDto;
import ru.itgirl.libproject_new.dto.author.request.AuthorUpdateDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name);

    AuthorDto getAuthorByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    void deleteAuthor(Long id);
}