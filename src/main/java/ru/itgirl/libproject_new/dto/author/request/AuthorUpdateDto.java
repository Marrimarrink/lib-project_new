package ru.itgirl.libproject_new.dto.author.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorUpdateDto {
    @NotNull(message = "Id автора не заполнен")
    @Positive(message = "Некорректный Id автора, " +
            "id должен быть положительным числом")
    private Long id;
    @NotBlank(message = "Необходимо указать имя")
    @Pattern(regexp = "^[А-ЯЁ][А-ЯЁа-яё\\s-]*$", message = "Некорректно имя, " +
            "в имени присутствуют недопустимые символы")
    private String name;
    @NotBlank(message = "Необходимо указать фамилию")
    @Pattern(regexp = "^[А-ЯЁ][А-ЯЁа-яё\\s-]*$", message = "Некорректно имя, " +
            "в фамилии присутствуют недопустимые символы")
    private String surname;
}
