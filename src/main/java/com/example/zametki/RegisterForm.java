package com.example.zametki;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegisterForm {

    private String email;
    private String username;
    private String password;

    public User toUser(PasswordEncoder encoder) {
        return new User(email, username, encoder.encode(password));
    }
}
