package ru.ioann.transliterator.web.validator;

import ru.ioann.transliterator.enums.Language;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = LanguageValidator.class)
@Target({ ElementType.FIELD }) //По необходимости можно расширять диапазон применения
@Retention(RetentionPolicy.RUNTIME)
public @interface LanguageValues {

    String message() default "Invalid language";
    Language[] values() default {};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
