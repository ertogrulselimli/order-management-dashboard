package com.ertogrul.omsb2b.common.exception;

public  abstract class BaseException extends RuntimeException {

    protected int exceptionCode=ExceptionCodes.NON_STANDARD.getValue();

    protected String errorMessage;


    public BaseException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
