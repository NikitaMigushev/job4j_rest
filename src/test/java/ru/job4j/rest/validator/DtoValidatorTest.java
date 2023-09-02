package ru.job4j.rest.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.rest.dto.RegisterDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class DtoValidatorTest {
    private static DtoValidator validator;

    @BeforeAll
    public static void init() {
        validator = new DtoValidator();
    }

    @Test
    public void validateRegisterDtoSuccess() {
        RegisterDto registerDto = new RegisterDto("username", "password");
        assertThat(validator.validateRegisterDto(registerDto)).isTrue();
    }

    @Test
    public void validateRegisterDtoFails() {
        RegisterDto registerDto = new RegisterDto("", "");
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validateRegisterDto(registerDto);
        });
    }

    @Test
    public void validateRegisterDtoFailsWhenNull() {
        RegisterDto registerDto = new RegisterDto(null, null);
        assertThrows(IllegalArgumentException.class, () -> {
            validator.validateRegisterDto(registerDto);
        });
    }
}