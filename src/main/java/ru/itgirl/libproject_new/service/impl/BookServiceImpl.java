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
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findBookByNameBySql(name);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        }else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
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
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findOne(specification);
        if (book.isPresent()) {
            BookDto bookDto = convertEntityToDto(book.get());
            log.info("Book: {}", bookDto.toString());
            return bookDto;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

//ошибка  в методе
/*    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Try to create book: {}", bookCreateDto);
        if (bookRepository.existsByNameAndGenre(
                bookCreateDto.getName(),
                bookCreateDto.getGenreId())) {
            log.warn("Book {} {} already exists",
                    bookCreateDto.getName(),
                    bookCreateDto.getGenreId());
        }
        try {
            Book book = convertDtoToEntity(bookCreateDto);
            Book savedBook = bookRepository.save(book);
            log.info("Book saved with Id: {}", savedBook.getId());
            BookDto bookDto = convertEntityToDto(savedBook);
            log.info("Book created: {}", bookDto);
            return bookDto;
        } catch (Exception e) {
            log.error("Error creating book: {}", e.getMessage());
            throw new NoSuchElementException("Failed to create book", e);
        }
    }*/

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Try to create book: {}", bookCreateDto);

        if (bookRepository.existsByNameAndGenre_Id(
                bookCreateDto.getName(),
                bookCreateDto.getGenreId())) {
            log.error("Book {} в жанре {} уже существует",
                    bookCreateDto.getName(),
                    bookCreateDto.getGenreId());
        }
        try {
            Book book = convertDtoToEntity(bookCreateDto);
            Book savedBook = bookRepository.save(book);
            log.info("Book saved with Id: {}", savedBook.getId());
            return convertEntityToDto(savedBook);
        } catch (Exception e) {
            log.error("Error creating book: {}", e.getMessage());
            throw new RuntimeException("Failed to create book", e); // Более информативно
        }
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
        log.info("Try to update book: {}", bookUpdateDto);
        Book book = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow(() -> {
                    log.error("Book with ID {} not found", bookUpdateDto.getId());
                    return new NoSuchElementException("Book with ID " + bookUpdateDto.getId() + " not found");
                });
        book.setName(bookUpdateDto.getName());
        Genre genre = genreRepository.findById(bookUpdateDto.getGenreId())
                .orElseThrow(() -> {
                    log.error("Genre with ID {} not found", bookUpdateDto.getId());
                    return new NoSuchElementException("Genre with ID " + bookUpdateDto.getId() + " not found");
                });
        book.setGenre(genre);
        Book savedBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(savedBook);
        log.info("Book updated: {}", bookDto);
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Try to find book by id {} for deleting", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            log.info("Book deleted: {}", id);
        } else {
            log.error("Book with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }



//старый метод convertEntityToDto до появления метода на create book

/*    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }*/


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


}
