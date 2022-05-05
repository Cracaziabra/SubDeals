package com.example.zametki;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegisterForm {

    @NotBlank(message = "Укажите адрес электронной почты")
    private String email;

    @NotBlank(message = "Укажите имя пользователя")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    public User toUser(PasswordEncoder encoder) {
        return new User(email, username, encoder.encode(password));
    }
}
