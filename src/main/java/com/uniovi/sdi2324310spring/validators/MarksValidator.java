package com.uniovi.sdi2324310spring.validators;

import com.uniovi.sdi2324310spring.entities.Mark;
import com.uniovi.sdi2324310spring.entities.User;
import com.uniovi.sdi2324310spring.services.MarksService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MarksValidator implements Validator {
    private final MarksService marksService;

    public MarksValidator(MarksService marksService) {
        this.marksService = marksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Mark.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Mark mark=(Mark) target;
        if(mark.getScore()<0 || mark.getScore()>10){
            errors.rejectValue("score", "Error.mark.score.invalid");
        }
        if(mark.getDescription().length()<20){
            errors.rejectValue("description", "Error.mark.description.length");
        }
    }
}
