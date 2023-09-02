package ru.job4j.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ru.job4j.rest.model.UserEntity;
import ru.job4j.rest.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private AuthenticationManager authenticationManager;


    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public Collection<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        var person = userService.findById(id);
        return new ResponseEntity<UserEntity>(
                person.orElse(new UserEntity()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody UserEntity userEntity) {
        var updatedUser = userService.save(userEntity);
        if (updatedUser.isEmpty()) {
            return new ResponseEntity<>("User has not been updated", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        var result = userService.deleteById(id);
        if (!result) {
            return new ResponseEntity<>("User has not been deleted", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().build();
    }
}
