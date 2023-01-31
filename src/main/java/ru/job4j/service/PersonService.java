package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository persons;

    public List<Person> findAll() {
        return (List<Person>) persons.findAll();
    }

    public Optional<Person> findById(int id) {
        return persons.findById(id);
    }

    public Person save(Person person) {
        return persons.save(person);
    }

    public boolean delete(int id) {
        var findThisPerson = persons.findById(id);
        findThisPerson.ifPresent(persons::delete);
        return findThisPerson.isPresent();
    }

    public boolean update(Person person) {
        var findThisPerson = persons.findById(person.getId());
        if (findThisPerson.isPresent()) {
            persons.save(person);
            return true;
        }
        return false;
    }
}
