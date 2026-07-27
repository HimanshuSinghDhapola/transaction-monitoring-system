package com.fintech.transaction_monitoring_system.controller;

import com.fintech.transaction_monitoring_system.dto.response.ApiResponse;
import com.fintech.transaction_monitoring_system.dto.response.TransactionUploadResponse;
import com.fintech.transaction_monitoring_system.enums.SuccessCode;
import com.fintech.transaction_monitoring_system.service.TransactionUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/transaction-uploads")
@RequiredArgsConstructor
public class TransactionUploadController {

    private final TransactionUploadService transactionUploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<TransactionUploadResponse>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails){
        TransactionUploadResponse response = transactionUploadService.uploadFile(file, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(SuccessCode.UPLOAD_S001, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionUploadResponse>> getUploadById(@PathVariable UUID id){
        TransactionUploadResponse response = transactionUploadService.getUploadById(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.UPLOAD_S001, response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionUploadResponse>>> getAllUploads
            (@PageableDefault(size = 10, sort = "createdAt")Pageable pageable){
        Page<TransactionUploadResponse> response = transactionUploadService.getAllUploads(pageable);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.UPLOAD_S001, response));
    }
}
