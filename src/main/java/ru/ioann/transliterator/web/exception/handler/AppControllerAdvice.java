package ru.ioann.transliterator.web.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ioann.transliterator.TransliteratorApplication;
import ru.ioann.transliterator.web.dto.ValidationErrorDto;

import java.util.HashMap;
import java.util.function.Function;

@ControllerAdvice(assignableTypes = TransliteratorApplication.class)
public final class AppControllerAdvice extends ResponseEntityExceptionHandler {

    //Мапа с функциями будет получше, чем swith или куча if'ов. По крайней мере читабельно.
    //Возможно, есть смысл вернуться к switch, когда в java появится type inference по аналогии со scala.
    private static final
    HashMap<Class<? extends ObjectError>, Function<? super ObjectError, String>>
            METHOD_ARGUMENT_NOT_VALID_MESSAGE_BUILDERS = new HashMap<>();

    static {
        METHOD_ARGUMENT_NOT_VALID_MESSAGE_BUILDERS.put(ObjectError.class, AppControllerAdvice::messageFromObjectError);
        METHOD_ARGUMENT_NOT_VALID_MESSAGE_BUILDERS.put(FieldError.class, AppControllerAdvice::messageFromFieldError);
    }

    private static String messageFromObjectError(ObjectError error) {
        return error.getObjectName() + ":" + error.getDefaultMessage();
    }

    private static String messageFromFieldError(ObjectError error) {
        FieldError fieldError = (FieldError) error;
        return fieldError.getField() + ":" + error.getDefaultMessage();
    }


    //Можно переопределить еще много чего, но в этом задании делается акцент именно на enum Languages
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationErrorDto errorDto = new ValidationErrorDto();

        errorDto.status = status;

        BindingResult br = ex.getBindingResult();
        ObjectError error = br.getAllErrors().get(0);
        errorDto.errorMessage = METHOD_ARGUMENT_NOT_VALID_MESSAGE_BUILDERS.get(error.getClass()).apply(error);

        return super.handleExceptionInternal(ex, errorDto, headers, status, request);
    }
}
