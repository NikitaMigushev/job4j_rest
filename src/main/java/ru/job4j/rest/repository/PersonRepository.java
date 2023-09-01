package ru.job4j.rest.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.rest.model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    List<Person> findAll();
}
