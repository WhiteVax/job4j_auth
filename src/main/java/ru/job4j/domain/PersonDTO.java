package ru.job4j.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonDTO {
    private String login;
    private String password;
}
