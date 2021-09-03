package com.insoftar.apiusers.exception;

import com.insoftar.apiusers.constants.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private Error processFieldErrors(List<FieldError> fieldErrors) {
        Error error = new Error(Constants.VALID_ERROR);
        for (FieldError fieldError : fieldErrors) {
            error.addFieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    @Getter
    @Setter
    static class Error {
        private final String message;
        public List<FieldErrorHandler> fieldErrors = new ArrayList<>();

        Error(String message) {
            this.message = message;
        }

        public void addFieldError(String objectName, String field, String message) {
            FieldErrorHandler error = new FieldErrorHandler(field, message);
            fieldErrors.add(error);
        }
    }

    @Getter
    @Setter
    static class FieldErrorHandler {
        private String field;
        private String message;

        public FieldErrorHandler(String field, String message) {
            this.message = message;
            this.field = field;
        }
    }
}
