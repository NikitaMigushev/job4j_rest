package ru.job4j.rest.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import ru.job4j.rest.dto.RegisterDto;

import java.util.Set;

@Component
public class DtoValidator {
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public boolean validateRegisterDto(RegisterDto registerDto) {
       Set<ConstraintViolation<RegisterDto>> violations = VALIDATOR.validate(registerDto);
       if (!violations.isEmpty()) {
           throw new IllegalArgumentException("RegisterDto has not passed validation", new ConstraintViolationException(violations));
       }
       return true;
    }
}
