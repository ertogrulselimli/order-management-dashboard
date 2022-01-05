package com.ertogrul.omsb2b.service.exceptions;

import com.ertogrul.omsb2b.common.exception.StandardException;

public class ManagerNotFoundException extends StandardException {

    public ManagerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
