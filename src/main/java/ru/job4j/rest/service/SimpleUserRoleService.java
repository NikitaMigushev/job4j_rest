package ru.job4j.rest.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.rest.model.UserRole;
import ru.job4j.rest.repository.UserRoleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class SimpleUserRoleService implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Optional<UserRole> save(UserRole userRole) {
        return Optional.ofNullable(userRoleRepository.save(userRole));
    }

    @Override
    public boolean deleteById(Long id) {
        userRoleRepository.deleteById(id);
        return !userRoleRepository.existsById(id);
    }

    @Override
    public Optional<UserRole> findById(Long id) {
        return userRoleRepository.findById(id);
    }

    @Override
    public Collection<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public Optional<UserRole> findByAuthority(String userRole) {
        return userRoleRepository.findByRole(userRole);
    }
}
