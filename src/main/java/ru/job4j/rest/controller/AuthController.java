package ru.job4j.rest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.rest.dto.AuthResponseDto;
import ru.job4j.rest.dto.LoginDto;
import ru.job4j.rest.dto.RegisterDto;
import ru.job4j.rest.security.JWTGenerator;
import ru.job4j.rest.service.UserService;
import ru.job4j.rest.validator.DtoValidator;

import java.io.IOException;
import java.util.HashMap;


@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    private final DtoValidator validator;

    private JWTGenerator jwtGenerator;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          DtoValidator validator,
                          JWTGenerator jwtGenerator,
                          ObjectMapper objectMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.validator = validator;
        this.jwtGenerator = jwtGenerator;
        this.objectMapper = objectMapper;
    }


    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        var savedUser = userService.save(registerDto);
        if (savedUser.isEmpty()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("User has been registered successfully", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new AuthResponseDto(token));
        } catch (AuthenticationException e) {
            throw new RuntimeException("User has not been authorized. Check username or password", e);
        }
    }

    @ExceptionHandler(value = { AuthenticationException.class })
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
