package com.fintech.transaction_monitoring_system.exception;

import com.fintech.transaction_monitoring_system.enums.ErrorCode;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
