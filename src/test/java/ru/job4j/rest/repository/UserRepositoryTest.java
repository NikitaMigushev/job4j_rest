package ru.job4j.rest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.rest.model.UserEntity;
import ru.job4j.rest.model.UserRole;
import ru.job4j.rest.service.SimpleUserService;
import ru.job4j.rest.service.UserRoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    SimpleUserService userService;

    private Set<UserRole> userAuthorities = new HashSet<>();

    @BeforeEach
    public void beforeEach() {
        var savedUserRole = userRoleService.save(new UserRole("ROLE_USER"));
        userAuthorities.add(savedUserRole.get());
    }

    @RepeatedTest(2)
    void saveRetrieve() {
        var savedUser = userService.save(new UserEntity("Test1", "123", userAuthorities));
        List<UserEntity> users = userRepository.findAll();
        assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals("Test1", users.get(0).getUsername())
        );
    }

    @RepeatedTest(2)
    void findByNameTest() {
        userRepository.save(new UserEntity("Test1", "123", userAuthorities));
        var foundUser = userRepository.findByUsername("Test1");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getPassword()).isEqualTo("123");
    }

    @RepeatedTest(2)
    void deleteByIdTest() {
        UserEntity user = userRepository.save(new UserEntity("Test1", "123", userAuthorities));
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }


    @Test
    void findByUserNameAndPasswordTest() {
        userRepository.save(new UserEntity("Test1", "123", userAuthorities));
        userRepository.save(new UserEntity("Test2", "321", userAuthorities));
        var foundUser = userRepository.findByUsernameAndPassword("Test2", "321");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("Test2");
    }

    @RepeatedTest(2)
    void existsByUsernametest() {
        var savedUser = userRepository.save(new UserEntity("Test777", "123", userAuthorities));
        var resultTrue = userRepository.existsByUsername(savedUser.getUsername());
        var resultFalse = userRepository.existsByUsername("someuserThatdoesn'texist");
        assertThat(resultTrue).isTrue();
        assertThat(resultFalse).isFalse();
    }


}