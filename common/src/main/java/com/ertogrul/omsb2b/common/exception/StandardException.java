package com.ertogrul.omsb2b.common.exception;

public class StandardException extends BaseException {


    public StandardException(String errorMessage) {
        super(errorMessage);
        this.exceptionCode=ExceptionCodes.STANDARD.getValue();
    }




}
