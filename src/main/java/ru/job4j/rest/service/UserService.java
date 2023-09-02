package ru.job4j.rest.service;

import ru.job4j.rest.dto.RegisterDto;
import ru.job4j.rest.model.UserEntity;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> save(UserEntity accidentUser);
    boolean update(UserEntity updatedUserEntity);
    boolean deleteById(Long id);
    Optional<UserEntity> findByUsername(String username);
    Collection<UserEntity> findAll();
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
    boolean existsByUserName(String username);
    Optional<UserEntity> save(RegisterDto registerDto);
    Optional<UserEntity> findById(Long id);
}
