package ru.itgirl.libproject_new.service;

import ru.itgirl.libproject_new.dto.genre.response.GenreDto;

public interface GenreService {
    GenreDto getGenreById(Long id);
}
