package com.wms.exception;

public class MyCustomException extends Exception {
    private int errorCode;

    public MyCustomException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
