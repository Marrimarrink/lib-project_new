package ru.itgirl.libproject_new.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libproject_new.dto.author.response.AuthorDto;
import ru.itgirl.libproject_new.dto.book.request.BookCreateDto;
import ru.itgirl.libproject_new.dto.book.request.BookUpdateDto;
import ru.itgirl.libproject_new.dto.book.response.BookDto;
import ru.itgirl.libproject_new.model.Author;
import ru.itgirl.libproject_new.model.Book;
import ru.itgirl.libproject_new.model.Genre;
import ru.itgirl.libproject_new.repository.AuthorRepository;
import ru.itgirl.libproject_new.repository.BookRepository;
import ru.itgirl.libproject_new.repository.GenreRepository;
import ru.itgirl.libproject_new.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    //private final AuthorRepository authorRepository;
/*    //Метод без логирования
    @Override
    public BookDto getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDto(book);
    }*/

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDto getByNameV2(String name) {
        Book book = bookRepository.findBookByNameBySql(name).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });

        Book book = bookRepository.findOne(specification).orElseThrow();
        return convertEntityToDto(book);
    }

    //старый метод convertEntityToDto до появления метода на create book

/*    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }*/

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book  = bookRepository.save(convertDtoToEntity(bookCreateDto));
        BookDto bookDto = convertEntityToDto(book);
        return bookDto;
    }

    private Book convertDtoToEntity(BookCreateDto bookCreateDto) {
        //бага  "genre": "ru.itgirl.libproject_new.model.Genre@525e1f2f",
        /*Genre genre = new Genre();
        genre.setId(bookCreateDto.getGenreId());*/
        //фикс баги:
        Genre genre = genreRepository.findById(bookCreateDto.getGenreId())
                .orElseThrow(() -> new IllegalArgumentException("Такого жанра нет в базе данных"));

        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre) //проблема исправлена
                .build();
    }

    private BookDto convertEntityToDto(Book book) {

        String genreName = "Неизвестно";
        if (book.getGenre() != null) {
            genreName = book.getGenre().getName();
        }


        List<AuthorDto> authors = new ArrayList<>();
        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                authors.add(
                        AuthorDto.builder()
                                .id(author.getId())
                                .name(author.getName())
                                .surname(author.getSurname())
                                .build()
                );
            }
        }

        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(genreName)
                .authors(authors)
                .build();
    }

/*    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        book.setGenre(bookUpdateDto.getGenreId()); //тут ошибка
        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        return bookDto;
    }*/

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
        book.setName(bookUpdateDto.getName());
        Genre genre = genreRepository.findById(bookUpdateDto.getGenreId())
                .orElseThrow(() -> new EntityNotFoundException("Жанр не найден"));
        book.setGenre(genre);
        Book savedBook = bookRepository.save(book);
        return convertEntityToDto(savedBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }


}
