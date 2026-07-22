package com.fintech.transaction_monitoring_system.service.impl;

import com.fintech.transaction_monitoring_system.dto.response.TransactionResponse;
import com.fintech.transaction_monitoring_system.entity.Transaction;
import com.fintech.transaction_monitoring_system.enums.ErrorCode;
import com.fintech.transaction_monitoring_system.exception.BusinessException;
import com.fintech.transaction_monitoring_system.repository.TransactionRepository;
import com.fintech.transaction_monitoring_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public Page<TransactionResponse> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public TransactionResponse getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.GEN_E001));
        return toResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> getTransactionsByUpload(UUID uploadId, Pageable pageable) {
        return transactionRepository.findByUploadId(uploadId, pageable).map(this::toResponse);
    }

    private TransactionResponse toResponse(Transaction transaction){
        return TransactionResponse.builder()
                .id(transaction.getId())
                .uploadId(transaction.getUpload().getId())
                .accountNumber(transaction.getAccountNumber())
                .transactionReference(transaction.getTransactionReference())
                .amount(transaction.getAmount())
                .currency(transaction.getCurrency())
                .transactionDate(transaction.getTransactionDate())
                .merchantName(transaction.getMerchantName())
                .channel(transaction.getChannel())
                .location(transaction.getLocation())
                .isFlagged(transaction.isFlagged())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
