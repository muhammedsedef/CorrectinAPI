package com.correctin.demo.util;

import com.correctin.demo.entity.User;
import com.correctin.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;


    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        return userRepository.findByEmail(email) != null;
    }
}
