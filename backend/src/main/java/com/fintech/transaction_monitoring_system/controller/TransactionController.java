package com.fintech.transaction_monitoring_system.controller;

import com.fintech.transaction_monitoring_system.dto.response.ApiResponse;
import com.fintech.transaction_monitoring_system.dto.response.TransactionResponse;
import com.fintech.transaction_monitoring_system.enums.SuccessCode;
import com.fintech.transaction_monitoring_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getAllTransactions(
            @PageableDefault(size = 10, sort = "transactionDate") Pageable pageable){
        Page<TransactionResponse> response = transactionService.getAllTransactions(pageable);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TXN_S001, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionResponse>> getTransactionById(@PathVariable UUID id){
        TransactionResponse response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TXN_S001, response));
    }

    @GetMapping("/upload/{uploadId}")
    public ResponseEntity<ApiResponse<Page<TransactionResponse>>> getTransactionByUpload(
            @PathVariable UUID uploadId,
            @PageableDefault(size = 10, sort = "transactionDate") Pageable pageable){
        Page<TransactionResponse> response = transactionService.getTransactionsByUpload(uploadId, pageable);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.TXN_S001, response));
    }

}
