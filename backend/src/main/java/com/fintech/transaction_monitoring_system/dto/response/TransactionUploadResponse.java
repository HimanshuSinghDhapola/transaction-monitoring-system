package com.fintech.transaction_monitoring_system.dto.response;

import com.fintech.transaction_monitoring_system.enums.UploadStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class TransactionUploadResponse {
    private UUID id;
    private String fileName;
    private UploadStatus status;
    private int totalRecords;
    private int processedRecords;
    private int failedRecords;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
