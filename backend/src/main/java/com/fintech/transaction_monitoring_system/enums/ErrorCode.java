package com.fintech.transaction_monitoring_system.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Auth errors
    AUTH_E001("AUTH_E001", "Username already taken", HttpStatus.CONFLICT),
    AUTH_E002("AUTH_E002", "Email already registered", HttpStatus.CONFLICT),
    AUTH_E003("AUTH_E003", "Invalid username or password", HttpStatus.UNAUTHORIZED),
    AUTH_E004("AUTH_E004", "Account is disabled", HttpStatus.FORBIDDEN),

    // General errors
    GEN_E001("GEN_E001", "Resource not found", HttpStatus.NOT_FOUND),
    GEN_E002("GEN_E002", "Validation failed", HttpStatus.BAD_REQUEST),
    GEN_E003("GEN_E003", "Access denied", HttpStatus.FORBIDDEN),
    GEN_E004("GEN_E004", "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),

    // Upload errors
    UPLOAD_E001("UPLOAD_E001", "Empty file", HttpStatus.BAD_REQUEST),
    UPLOAD_E002("UPLOAD_E002", "Wrong file type", HttpStatus.BAD_REQUEST),
    UPLOAD_E003("UPLOAD_E003", "File could not be parsed", HttpStatus.BAD_REQUEST),
    UPLOAD_E004("UPLOAD_E004", "File exceeds maximum upload size", HttpStatus.CONTENT_TOO_LARGE);


    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status){
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }

}
