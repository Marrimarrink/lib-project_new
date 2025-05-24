package ru.itgirl.libproject_new.dto.book.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookUpdateDto {
    @NotNull(message = "Id книги не заполнен")
    @Positive(message = "Некорректный Id книги, id должен быть положительным числом")
    private Long id;
    @Size(min = 3, max = 200)
    @NotBlank(message = "Необходимо указать название книги")
    @Pattern(regexp = "^[\\p{L}0-9\\s.,!?-]+$", message = "Некорректное название книги, в названии присутствуют недопустимые символы")
    private String name;
    @NotNull(message = "Id жанра не заполнен")
    @Positive(message = "Некорректный Id жанра, id должен быть положительным числом")
    private Long genreId;
}