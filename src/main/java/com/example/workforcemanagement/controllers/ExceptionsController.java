package com.example.workforcemanagement.controllers;

import com.example.workforcemanagement.exceptions.ProvisioningException;
import com.example.workforcemanagement.exceptions.TaskException;
import com.example.workforcemanagement.exceptions.UserException;
import jakarta.validation.ConstraintViolationException;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<String> handleTaskException(TaskException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(ExecutionControl.UserException.class)
    public ResponseEntity<String> handleUserException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleEnumError(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>("Status " + ex.getValue() + "not allowed", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleEnumBindingError(IllegalArgumentException ex) {
        return new ResponseEntity<>("Invalid input: unsupported value for field", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body("Invalid format");
    }

    @ExceptionHandler(ProvisioningException.class)
        public ResponseEntity<String> handleProvisioningException(ProvisioningException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

}
