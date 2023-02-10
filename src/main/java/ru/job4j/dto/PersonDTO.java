package ru.job4j.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class PersonDTO {
    @NotNull(message = "Login must be not null.")
    @Size(min = 3, max = 30, message = "Login is too short or too long.")
    private String login;
    @NotNull(message = "Password must be not null.")
    @Size(min = 5, max = 60, message = "Password must be between 8 and 60 characters.")
    private String password;
}
