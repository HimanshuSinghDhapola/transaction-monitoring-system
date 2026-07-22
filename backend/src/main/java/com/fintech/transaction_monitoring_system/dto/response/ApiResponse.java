package com.fintech.transaction_monitoring_system.dto.response;

import com.fintech.transaction_monitoring_system.enums.SuccessCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data){
        return ApiResponse.<T>builder()
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }
}
