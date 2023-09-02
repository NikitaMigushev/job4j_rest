package ru.job4j.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.rest.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAll();
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
    void deleteById(Long id);

    Optional<UserEntity> findById(Long id);
    boolean existsByUsername(String username);
}
