package ru.job4j.rest.service;

import ru.job4j.rest.model.UserRole;

import java.util.Collection;
import java.util.Optional;

public interface UserRoleService {
    Optional<UserRole> save(UserRole userRole);
    boolean deleteById(Long id);
    Optional<UserRole> findById(Long id);
    Collection<UserRole> findAll();
    Optional<UserRole> findByAuthority(String userRole);

}
