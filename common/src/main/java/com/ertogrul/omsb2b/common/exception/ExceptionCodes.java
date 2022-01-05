package com.ertogrul.omsb2b.common.exception;

public enum ExceptionCodes {

    STANDARD(1001),
    NON_STANDARD(1002);

    private final int value;

    ExceptionCodes(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

}
