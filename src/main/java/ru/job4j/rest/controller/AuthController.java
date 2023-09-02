package ru.job4j.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.rest.dto.AuthResponseDto;
import ru.job4j.rest.dto.LoginDto;
import ru.job4j.rest.dto.RegisterDto;
import ru.job4j.rest.security.JWTGenerator;
import ru.job4j.rest.service.UserService;
import ru.job4j.rest.validator.DtoValidator;


@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    private final DtoValidator validator;

    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          DtoValidator validator,
                          JWTGenerator jwtGenerator) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.validator = validator;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        validator.validateRegisterDto(registerDto);
        if (userService.existsByUserName(registerDto.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        userService.save(registerDto);
        return new ResponseEntity<>("User has been registered successfully", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }
}
