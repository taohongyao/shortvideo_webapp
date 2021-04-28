package shortvideo.declantea.me.validation;

import shortvideo.declantea.me.validationannotation.ValidNameInput;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DisplayNameValidator implements ConstraintValidator<ValidNameInput, String> {

    private final String PATTERN = "^[a-zA-Z0-9_]{5,}$"; //only letters, numbers and _(underline); at least 5 characters

    @Override
    public void initialize(ValidNameInput constraintAnnotation) {

    }

    @Override
    public boolean isValid(String displayName, ConstraintValidatorContext context) {
//        System.out.println("check name ");
        return Pattern.compile(PATTERN).matcher(displayName).matches();
    }
}
