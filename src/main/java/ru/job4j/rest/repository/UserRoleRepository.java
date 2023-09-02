package ru.job4j.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.rest.model.UserRole;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByRole(String userRole);
    void deleteById(Long id);

    Optional<UserRole> findById(Long id);

    boolean existsById(Long id);
}
