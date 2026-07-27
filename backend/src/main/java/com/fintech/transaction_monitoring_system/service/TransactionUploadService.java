package com.fintech.transaction_monitoring_system.service;

import com.fintech.transaction_monitoring_system.dto.response.TransactionUploadResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface TransactionUploadService {
    TransactionUploadResponse uploadFile(MultipartFile file, String userName);
    TransactionUploadResponse getUploadById(UUID id);
    Page<TransactionUploadResponse> getAllUploads(Pageable pageable);
}
