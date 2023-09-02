package ru.job4j.rest.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.rest.dto.RegisterDto;
import ru.job4j.rest.model.UserEntity;

@Component
public class RegisterDtoUserConverter {
    private final ModelMapper modelMapper;

    @Autowired
    public RegisterDtoUserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity convertRegisterDtoToUserEntity(RegisterDto registerDto) {
        return modelMapper.map(registerDto, UserEntity.class);
    }
}
