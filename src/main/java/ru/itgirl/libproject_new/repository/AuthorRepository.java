package ru.itgirl.libproject_new.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itgirl.libproject_new.model.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    Optional<Author> findAuthorByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM AUTHOR WHERE name = ?")
    Optional<Author> findAuthorByNameBySql(String name);

    boolean existsByNameAndSurname(@Size(min = 3, max = 20) @NotBlank(message = "Необходимо указать имя") @Pattern(regexp = "^[А-ЯЁ][А-ЯЁа-яё\\s-]*$", message = "Некорректно имя, " +
            "в имени присутствуют недопустимые символы") String name, @NotBlank(message = "Необходимо указать фамилию") @Pattern(regexp = "^[А-ЯЁ][А-ЯЁа-яё\\s-]*$", message = "Некорректно имя, " +
            "в фамилии присутствуют недопустимые символы") String surname);
}