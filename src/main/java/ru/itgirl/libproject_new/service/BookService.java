package ru.itgirl.libproject_new.service;

import ru.itgirl.libproject_new.dto.book.response.BookDto;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);
}