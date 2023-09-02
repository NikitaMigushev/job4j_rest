package ru.job4j.rest.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.job4j.rest.configuration.ModelMapperConfig;
import ru.job4j.rest.dto.RegisterDto;
import ru.job4j.rest.model.UserEntity;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterDtoUserConverterTest {

    private static ModelMapper modelMapper;
    private static RegisterDtoUserConverter converter;

    @BeforeAll
    public static void init() {
        modelMapper = new ModelMapperConfig().modelMapper();
        converter = new RegisterDtoUserConverter(modelMapper);
    }


    @Test
    public void convertRegisterDtoToUserEntityTest() {
        RegisterDto registerDto = new RegisterDto("username", "secretpassword");
        UserEntity user = converter.convertRegisterDtoToUserEntity(registerDto);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("username");
        assertThat(user.getPassword()).isEqualTo("secretpassword");
    }
}