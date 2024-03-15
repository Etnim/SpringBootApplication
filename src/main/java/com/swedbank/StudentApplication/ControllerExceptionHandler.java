package com.swedbank.StudentApplication;

import com.swedbank.StudentApplication.common.ErrorMessage;
import com.swedbank.StudentApplication.person.exception.PersonException;
import com.swedbank.StudentApplication.person.exception.PersonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice //executed when controller runs
//140.18
public class ControllerExceptionHandler {
    Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class.getName());

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorMessage> notfoundException(PersonException e, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), e.getMessage(), request.getDescription(false));
        log.error(message.toString());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception e, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
