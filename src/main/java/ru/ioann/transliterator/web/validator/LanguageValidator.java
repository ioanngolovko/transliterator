package ru.ioann.transliterator.web.validator;

import ru.ioann.transliterator.enums.Language;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

public final class LanguageValidator implements ConstraintValidator<LanguageValues, Language> {

    private Language[] availableLanguages;

    @Override
    public void initialize(LanguageValues constraintAnnotation) {
        this.availableLanguages = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Language language, ConstraintValidatorContext constraintValidatorContext) {
        return Stream.of(availableLanguages)
                .anyMatch(l -> l == language);
    }
}
