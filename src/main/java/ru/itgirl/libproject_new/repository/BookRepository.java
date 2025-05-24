package ru.itgirl.libproject_new.repository;

import jakarta.validation.constraints.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itgirl.libproject_new.model.Book;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Optional<Book> findBookByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM BOOK WHERE name = ?")
    Optional<Book> findBookByNameBySql(String name);

    boolean existsByNameAndGenre_Id(@Size(min = 3, max = 200)
                                 @NotBlank(message = "Необходимо указать название книги")
                                 @Pattern(regexp = "^[\\p{L}0-9\\s.,!?-]+$", message = "Некорректное название книги," +
                                         " в названии присутствуют недопустимые символы") String name,
                                 @NotNull(message = "Id жанра не заполнен")
                                 @Positive(message = "Некорректный Id жанра, id должен быть положительным числом")
                                 Long genreId);
}