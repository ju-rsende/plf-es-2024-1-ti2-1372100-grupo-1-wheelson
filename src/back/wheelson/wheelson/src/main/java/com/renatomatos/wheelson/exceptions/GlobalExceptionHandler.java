package com.renatomatos.wheelson.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.UnexpectedTypeException;

import javax.validation.ConstraintViolationException;



import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details.");
        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    
    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

    //     var fieldErrors = e.getFieldErrors()
    //     .stream()
    //     .map( f -> new InvalidParam(f.getField(), f.getDefaultMessage()))
    //     .toList();

    //     var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

    //     pb.setTitle("your request parameters didn't pass the validation");
    //     pb.setProperty("invalid-params", fieldErrors);

    //     return null;
    // }

    // private record InvalidParam(String fieldName, String reason){}


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception exception,
            WebRequest request) {
        final String errorMessage = "Erro desconhecido";
        log.error(errorMessage, exception);
        return buildErrorResponse(
                exception,
                errorMessage,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException (
        DataIntegrityViolationException dataIntegrityViolationException,
        WebRequest request) {
        final String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
        log.error("Erro ao salvar entidade com problemas de integridade: " + errorMessage, dataIntegrityViolationException);
        return buildErrorResponse(
            dataIntegrityViolationException,
            errorMessage,
            HttpStatus.CONFLICT,
            request);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleUnexpectedTypeException(
            UnexpectedTypeException exception,
            WebRequest request) {
        log.error("Erro de tipo inesperado", exception);
        
        return buildErrorResponse(
            exception,
            HttpStatus.UNPROCESSABLE_ENTITY,
            request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException exception,
            WebRequest request) {
        log.error("Erro de validação de entidade", exception);
        return buildErrorResponse(
            exception,
            HttpStatus.UNPROCESSABLE_ENTITY,
            request);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmailNotFoundException(
            EmailNotFoundException exception,
            WebRequest request) {
        log.error("Email not found", exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleSenhaIncorretaException(SenhaIncorretaException exception, WebRequest request) {
        log.warn("Invalid login attempt", exception);
        return buildErrorResponse(exception, exception.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (this.printStackTrace) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
