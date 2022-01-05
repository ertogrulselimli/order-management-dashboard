package com.ertogrul.omsb2b.common.http;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class GenericError<T> {

    private int errorCode;

    private String errorMessage;

    public GenericError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private T addInfo;

}
