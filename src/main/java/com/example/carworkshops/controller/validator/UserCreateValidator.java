package com.example.carworkshops.controller.validator;

import com.example.carworkshops.controller.dto.UserCreateDto;
import com.example.carworkshops.model.User;
import com.example.carworkshops.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UserCreateValidator implements Validator {
    private static final String EXISTS = "already exists";

    private final UserRepository userRepository;

    public UserCreateValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCreateDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCreateDto dto = (UserCreateDto) o;

        List<User> usersByName = userRepository.findByNameAndDeletedAtIsNull(dto.getName());

        if (!CollectionUtils.isEmpty(usersByName)) {
            errors.rejectValue("name", EXISTS, EXISTS);
        }

        List<User> usersByEmail = userRepository.findByEmailAndDeletedAtIsNull(dto.getEmail());

        if (!CollectionUtils.isEmpty(usersByEmail)) {
            errors.rejectValue("email", EXISTS, EXISTS);
        }
    }
}
