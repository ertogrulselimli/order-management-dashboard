package com.ertogrul.omsb2b.service.exceptions;

import com.ertogrul.omsb2b.common.exception.BaseException;
import lombok.Getter;

import java.util.List;

@Getter
public class RoleManagersExistsException extends BaseException {

    private List<String> managers;

    public RoleManagersExistsException(String errorMessage,
                                       List<String> managers) {
        super(errorMessage);
        this.managers = managers;
    }
}
