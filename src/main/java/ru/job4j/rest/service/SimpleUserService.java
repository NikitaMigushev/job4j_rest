package ru.job4j.rest.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.rest.converter.RegisterDtoUserConverter;
import ru.job4j.rest.dto.RegisterDto;
import ru.job4j.rest.model.UserEntity;
import ru.job4j.rest.model.UserRole;
import ru.job4j.rest.repository.UserRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class SimpleUserService implements UserService {
    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    private final RegisterDtoUserConverter converter;

    @Override
    public Optional<UserEntity> save(UserEntity user) {
        var role = userRoleService.findByAuthority("ROLE_USER");
        var userRoles = new HashSet<UserRole>();
        userRoles.add(role.get());
        user.setRoles(userRoles);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var savedUser = userRepository.save(user);
        return Optional.ofNullable(userRepository.save(savedUser));
    }

    @Override
    public Optional<UserEntity> save(RegisterDto registerDto) {
        UserEntity user = converter.convertRegisterDtoToUserEntity(registerDto);
        return save(user);
    }

    @Override
    public boolean update(UserEntity updatedUserEntity) {
        Optional<UserEntity> existingUserOptional = userRepository.findByUsername(updatedUserEntity.getUsername());

        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();
            existingUser.setPassword(updatedUserEntity.getPassword());
            existingUser.setEnabled(updatedUserEntity.isEnabled());

            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Collection<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean existsByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}