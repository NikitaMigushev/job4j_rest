package ru.job4j.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.rest.model.UserEntity;
import ru.job4j.rest.service.UserService;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private AuthenticationManager authenticationManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;


    public UserController(final UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public Collection<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero.");
        }
        var person = userService.findById(id);
        if (!person.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
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

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
