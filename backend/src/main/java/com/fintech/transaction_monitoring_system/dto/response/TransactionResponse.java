package com.fintech.transaction_monitoring_system.dto.response;

import com.fintech.transaction_monitoring_system.enums.TransactionChannel;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TransactionResponse {
    private UUID id;
    private UUID uploadId;
    private String accountNumber;
    private String transactionReference;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime transactionDate;
    private String merchantName;
    private TransactionChannel channel;
    private String location;
    private boolean isFlagged;
    private LocalDateTime createdAt;
}
