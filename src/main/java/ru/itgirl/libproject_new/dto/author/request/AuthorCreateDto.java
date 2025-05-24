package ru.itgirl.libproject_new.dto.author.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthorCreateDto {
    @Size(min = 3, max = 20)
    @NotBlank(message = "Необходимо указать имя")
    @Pattern(regexp = "^[А-ЯЁ][А-ЯЁа-яё\\s-]*$", message = "Некорректно имя, " +
            "в имени присутствуют недопустимые символы")
    private String name;
    @NotBlank(message = "Необходимо указать фамилию")
    @Pattern(regexp = "^[А-ЯЁ][А-ЯЁа-яё\\s-]*$", message = "Некорректно имя, " +
            "в фамилии присутствуют недопустимые символы")
    private String surname;
}