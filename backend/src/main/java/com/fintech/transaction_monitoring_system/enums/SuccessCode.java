package com.fintech.transaction_monitoring_system.enums;

public enum SuccessCode {

    // Auth
    AUTH_S001("AUTH_S001", "Login successful"),
    AUTH_S002("AUTH_S002", "Account created successfully"),
    AUTH_S003("AUTH_S003", "Password changed successfully"),

    // Transaction
    TXN_S001("TXN_S001", "Transactions retrieved successfully");

    private final String code;
    private final String message;

    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
