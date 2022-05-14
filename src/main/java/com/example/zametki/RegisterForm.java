package com.example.zametki;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Data
@NoArgsConstructor
public class RegisterForm {

    @NotBlank(message = "Укажите адрес электронной почты")
    private String email;

    @NotBlank(message = "Укажите имя пользователя")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    private String firstName;
    private String secondName;
    private String thirdName;
    private Integer age;
    private String description;

    public RegisterForm(String firstName, String secondName, String thirdName, Integer age, String description, Date birthday) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.age = age;
        this.description = description;
        this.birthday = birthday;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public User toUser(PasswordEncoder encoder) {
        return new User(email, username, encoder.encode(password));
    }

    public void updateUser(User user) {
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setThirdName(thirdName);
        user.setBirthday(birthday);
        user.setAge(age);
        user.setDescription(description);
    }
}
