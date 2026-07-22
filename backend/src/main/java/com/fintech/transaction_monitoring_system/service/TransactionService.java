package com.fintech.transaction_monitoring_system.service;

import com.fintech.transaction_monitoring_system.dto.response.TransactionResponse;
import com.fintech.transaction_monitoring_system.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionService {
    Page<TransactionResponse> getAllTransactions(Pageable pageable);
    TransactionResponse getTransactionById(UUID id);
    Page<TransactionResponse> getTransactionsByUpload(UUID uploadId, Pageable pageable);
}
