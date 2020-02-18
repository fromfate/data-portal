package com.asiainfo.handler;

import com.asiainfo.dto.RestResponse;
import com.asiainfo.exception.*;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
public class ErrorHandler {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestResponse> resourceNotFoundHandler(ResourceNotFoundException exception) {
        logger.error("ResourceNotFoundException:",exception);
        return new ResponseEntity(new RestResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<RestResponse> ioExceptionHandler(IOException exception) {
        logger.error("IOException:",exception);
        return new ResponseEntity(new RestResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<RestResponse> AlreadyExistExceptionHandler(AlreadyExistException exception) {
        logger.error("AlreadyExistException:",exception);
        return new ResponseEntity(new RestResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<RestResponse> unAuthorizedHandler(UnAuthorizedException exception) {
        logger.error("UnAuthorizedException:",exception);
        return new ResponseEntity(new RestResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<RestResponse> forbiddenException(ForbiddenException exception) {
        logger.error("forbiddenException:",exception);
        return new ResponseEntity(new RestResponse(exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({BadRequestException.class, ConstraintViolationException.class})
    public ResponseEntity<RestResponse> badRequestExceptionHandler(Exception exception) {
        logger.error("BadRequestException:",exception);
        return new ResponseEntity(new RestResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }



}