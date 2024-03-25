package ru.skillbox.booking.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "Имя пользователя должно быть заполнено!")
    private String username;

    @NotBlank(message = "E-mail адрес пользователя должен быть заполнен!")
    private String email;

    @NotBlank(message = "Пароль пользователя не может быть пустым!")
    private String password;
}
