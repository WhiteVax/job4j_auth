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
        if (person.getLogin() == null || person.getPassword() == null) {
            throw new NullPointerException();
        }
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        return persons.save(person);
    }

    public void delete(Person person) {
        persons.delete(person);
    }

    public boolean update(Person person) {
        if (person.getLogin() == null || person.getPassword() == null) {
            throw new NullPointerException();
        }
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
