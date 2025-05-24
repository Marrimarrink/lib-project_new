package ru.itgirl.libproject_new.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgirl.libproject_new.dto.author.response.AuthorDto;
import ru.itgirl.libproject_new.dto.book.response.BookDto;
import ru.itgirl.libproject_new.dto.genre.response.GenreDto;
import ru.itgirl.libproject_new.model.Genre;
import ru.itgirl.libproject_new.repository.GenreRepository;
import ru.itgirl.libproject_new.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return convertToGenreDto(genre);
    }

    private GenreDto convertToGenreDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> {
                    List<AuthorDto> authorDtoList = book.getAuthors()
                            .stream()
                            .map(author -> AuthorDto.builder()
                                    .id(author.getId())
                                    .name(author.getName())
                                    .surname(author.getSurname())
                                    .build()
                            ).toList();

                    return BookDto.builder()
                            .id(book.getId())
                            .name(book.getName())
                            .genre(book.getGenre().getName())
                            .authors(authorDtoList)
                            .build();
                }).toList();

        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .books(bookDtoList)
                .build();
    }
}