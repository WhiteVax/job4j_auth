package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PersonRepository persons;

    public List<Person> findAll() {
        return persons.findAll();
    }

    public Optional<Person> findById(int id) {
        return persons.findById(id);
    }

    public Person save(Person person) {
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
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
            person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
            persons.save(person);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = persons.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}
