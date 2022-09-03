package com.ewisselectronic.sulama.sulamaservice.config;

import com.ewisselectronic.sulama.sulamaservice.model.error.ErrorConstants;
import com.ewisselectronic.sulama.sulamaservice.model.error.ErrorVM;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ExceptionTranslator {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
        ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION, "Validation error please check body fields and try again.");
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }
}
