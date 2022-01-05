package com.ertogrul.omsb2b.service.exceptions;

import com.ertogrul.omsb2b.common.exception.BaseException;
import com.ertogrul.omsb2b.common.exception.ExceptionCodes;

public class MockDeleteException2 extends BaseException {

    private String addInfo;

    public MockDeleteException2(String errorMessage,
                                String addInfo) {
        super(errorMessage);
        this.exceptionCode= ExceptionCodes.NON_STANDARD.getValue(); //Be sure to set this to NON_STANDART AS ui must know this
        this.addInfo=addInfo;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}
