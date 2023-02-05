package ru.job4j.controller;

import lombok.SneakyThrows;
import org.hibernate.type.TextType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.domain.Person;
import ru.job4j.domain.PersonDTO;
import ru.job4j.service.PersonService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonService persons;

    public PersonController(final PersonService persons) {
        this.persons = persons;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = persons.findById(id);
        return new ResponseEntity<>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @SneakyThrows
    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody PersonDTO person) {
        return new ResponseEntity<>(
                persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody Person person) {
        persons.update(person);
        var body = new HashMap<>() {{
            put("person", String.format("Person with id = %s successfully updated.", person.getId()));
        }}.toString();
        return ResponseEntity.status(HttpStatus.OK)
                .header("job4j_auth", "update")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
        var person = persons.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "This person is not found."
                )
        );
        persons.delete(person);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @PatchMapping("/")
    public ResponseEntity<HttpStatus> update(@RequestBody PersonDTO personDTO) {
        persons.update(personDTO);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
