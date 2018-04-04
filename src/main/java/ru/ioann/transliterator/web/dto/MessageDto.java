package ru.ioann.transliterator.web.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.ioann.transliterator.enums.Language;
import ru.ioann.transliterator.web.validator.LanguageValues;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import static ru.ioann.transliterator.enums.Language.*;

@JsonPropertyOrder({"language", "text"})
public final class MessageDto {

    /*
        DTO не несет в себе никакой логики. Вполне нормально, когда все поля public.
        В реальных проектах в логике, за исключением редких случаев, не участвует.
        Там слой domain и entity (если используется ORM, а не полумера типа MyBatis).
        Так что в реальных проектах надо бить на слои и использовать http://mapstruct.org/
     */

    @NotNull
    @LanguageValues(values = {RU, UA})
    public Language language;

    // Ограничение выставлено от балды. В любом случае оно должно иметься.
    @NotNull
    @Max(value = 500, message = "The maximum number of characters in text can not be more than 500")
    public String text;

}
