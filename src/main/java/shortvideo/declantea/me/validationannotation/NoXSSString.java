package shortvideo.declantea.me.validationannotation;


import shortvideo.declantea.me.validation.NoXSSStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NoXSSStringValidator.class)
@Documented
public @interface NoXSSString {
    String message() default "Invalid Input";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
