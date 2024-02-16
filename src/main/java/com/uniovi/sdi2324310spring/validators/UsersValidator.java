package com.uniovi.sdi2324310spring.validators;

import com.uniovi.sdi2324310spring.entities.User;
import com.uniovi.sdi2324310spring.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UsersValidator implements Validator {

    public UsersValidator() {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user=(User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Error.empty");
        if(user.getName().length()==0||
            user.getName().startsWith(" ")||
            user.getName().endsWith(" ")) {
            errors.rejectValue("name", "Error.adduser.name.invalid");
        }
        if(user.getLastName().length()==0||
            user.getLastName().startsWith(" ")||
            user.getLastName().endsWith(" ")) {
                errors.rejectValue("lastName", "Error.adduser.lastName.invalid");
        }
        if (user.getDni().length() != 9 ||
                !Character.isLetter(user.getDni().charAt(user.getDni().length()-1))) {
            errors.rejectValue("dni", "Error.adduser.dni.invalid");
        }
    }
}
