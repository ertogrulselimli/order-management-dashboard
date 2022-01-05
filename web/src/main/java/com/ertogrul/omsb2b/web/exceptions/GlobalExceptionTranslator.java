package com.ertogrul.omsb2b.web.exceptions;


import com.ertogrul.omsb2b.common.exception.StandardException;
import com.ertogrul.omsb2b.common.http.GenericError;
import com.ertogrul.omsb2b.service.exceptions.MockDeleteException2;
import com.ertogrul.omsb2b.service.exceptions.RoleManagersExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionTranslator {




/*    @ExceptionHandler({AuthenticationExpiredException.class})
    public ResponseEntity<Object> handleGeneralException(AuthenticationExpiredException ex) {
        log.error("error",ex);
        return new ResponseEntity<Object>(new GenericErrorResponse(ex.getMessage()),ex.getHttpStatus());
    }*/


    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAnauthorizedException(AuthenticationException ex, WebRequest webRequest) {
        return new ResponseEntity<Object>(new GenericError(13, "Bad Credentials username or password incorrect"), HttpStatus.UNAUTHORIZED);

    }

   @ExceptionHandler({MockDeleteException2.class})
   public ResponseEntity<GenericError<String>> handleMockDeleteException2(MockDeleteException2 ex){
       GenericError<String> genericError=new GenericError<>();
       genericError.setAddInfo(ex.getAddInfo());
       genericError.setErrorMessage(ex.getErrorMessage());
       genericError.setErrorCode(ex.getExceptionCode());
       return new ResponseEntity<GenericError<String>>(genericError,HttpStatus.BAD_REQUEST);
   }


    @ExceptionHandler({StandardException.class})
    public ResponseEntity<GenericError<Object>> handleStandardExceptions(StandardException baseException){
        GenericError genericError=new GenericError();
        genericError.setErrorCode(baseException.getExceptionCode());
        genericError.setErrorMessage(baseException.getErrorMessage());
        return new ResponseEntity<GenericError<Object>>(genericError,HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler({RoleManagersExistsException.class})
    public ResponseEntity<GenericError<List<String>>> handleNonStandard(RoleManagersExistsException baseException){
        GenericError genericError=new GenericError();
        genericError.setErrorCode(baseException.getExceptionCode());
        genericError.setErrorMessage(baseException.getErrorMessage());
        genericError.setAddInfo(baseException.getManagers());
        return new ResponseEntity<GenericError<List<String>>>(genericError,HttpStatus.BAD_REQUEST);
    }


  /*  @ExceptionHandler({Exception.class})
     public ResponseEntity<Object> handleGeneralException(Exception ex) {
        log.error("error",ex);
        return new ResponseEntity<Object>(new GenericErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
     }*/


}
