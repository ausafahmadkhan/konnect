package com.post.postplatform.advice;

import com.post.postcontract.response.BaseResponse;
import com.post.postplatform.utility.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandler {
    @Autowired
    private ResponseGenerator responseGenerator;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<BaseResponse> handleBadRequest(Exception e) {
        return new ResponseEntity<>(responseGenerator.getFailureResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<BaseResponse> handleInternalError(Exception e) {
        return new ResponseEntity<>(responseGenerator.getFailureResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
