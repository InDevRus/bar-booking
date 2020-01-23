package org.indevrus.barbooking.controllers.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.text.ParseException;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler({ServletRequestBindingException.class, ParseException.class, ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void wrongParameter() {
    }
}
