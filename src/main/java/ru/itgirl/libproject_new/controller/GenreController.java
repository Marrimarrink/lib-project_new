package ru.itgirl.libproject_new.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itgirl.libproject_new.dto.genre.response.GenreDto;
import ru.itgirl.libproject_new.service.GenreService;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre/{id}")
    GenreDto getGenreById(@PathVariable("id") Long id) {
        return genreService.getGenreById(id);
    }
}