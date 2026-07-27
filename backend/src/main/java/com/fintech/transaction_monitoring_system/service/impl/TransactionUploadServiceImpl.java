package com.fintech.transaction_monitoring_system.service.impl;

import com.fintech.transaction_monitoring_system.dto.response.TransactionUploadResponse;
import com.fintech.transaction_monitoring_system.entity.Transaction;
import com.fintech.transaction_monitoring_system.entity.TransactionUpload;
import com.fintech.transaction_monitoring_system.entity.User;
import com.fintech.transaction_monitoring_system.enums.ErrorCode;
import com.fintech.transaction_monitoring_system.enums.TransactionChannel;
import com.fintech.transaction_monitoring_system.enums.UploadStatus;
import com.fintech.transaction_monitoring_system.exception.BusinessException;
import com.fintech.transaction_monitoring_system.repository.TransactionRepository;
import com.fintech.transaction_monitoring_system.repository.TransactionUploadRepository;
import com.fintech.transaction_monitoring_system.repository.UserRepository;
import com.fintech.transaction_monitoring_system.service.TransactionUploadService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionUploadServiceImpl implements TransactionUploadService {

    private final TransactionUploadRepository transactionUploadRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private TransactionUploadResponse toResponse(TransactionUpload upload){
        return TransactionUploadResponse.builder()
                .id(upload.getId())
                .fileName(upload.getFileName())
                .status(upload.getStatus())
                .totalRecords(upload.getTotalRecords())
                .failedRecords(upload.getFailedRecords())
                .processedRecords(upload.getProcessedRecords())
                .createdAt(upload.getCreatedAt())
                .updatedAt(upload.getUpdatedAt())
                .build();
    }

    private Transaction parseRow(String[] row, TransactionUpload upload){
        String accountNumber = row[0];
        String transactionReference = row[1];
        BigDecimal amount = new BigDecimal(row[2].trim());
        String currency = row[3];
        LocalDateTime transactionDate = LocalDateTime.parse(row[4].trim());
        String merchantName = row[5];
        String channelRaw = row[6];
        TransactionChannel channel = (channelRaw == null ||
                channelRaw.isBlank())
                ? TransactionChannel.OTHER
                : TransactionChannel.valueOf(channelRaw.trim().toUpperCase(Locale.ROOT));
        String location = row[7];
        return Transaction.builder()
                .upload(upload)
                .accountNumber(accountNumber)
                .transactionReference(transactionReference)
                .amount(amount)
                .currency(currency)
                .transactionDate(transactionDate)
                .merchantName(merchantName)
                .channel(channel)
                .location(location)
                .build();
    }

    @Override
    public TransactionUploadResponse uploadFile(MultipartFile file, String userName) {
        if(file == null || file.isEmpty()){
            throw new BusinessException(ErrorCode.UPLOAD_E001);
        }
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null || !originalFileName.toLowerCase(Locale.ROOT).matches(".*\\.csv$")){
            throw new BusinessException(ErrorCode.UPLOAD_E002);
        }

        log.info("Upload requested: file={}, user={}", originalFileName, userName);

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new BusinessException(ErrorCode.GEN_E001));

        TransactionUpload upload = TransactionUpload.builder()
                .uploadedBy(user)
                .fileName(originalFileName)
                .status(UploadStatus.PENDING)
                .build();
        upload = transactionUploadRepository.save(upload);
        log.debug("Created upload record {} with status PENDING", upload.getId());

        upload.setStatus(UploadStatus.PROCESSING);
        upload = transactionUploadRepository.save(upload);

        int processed=0;
        int failed=0;
        int rowNumber=1;

        try(CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))){
            reader.readNext();
            String[] row;
            while((row = reader.readNext()) != null){
                rowNumber++;
                try{
                    Transaction txn = parseRow(row, upload);
                    transactionRepository.save(txn);
                    processed++;
                }catch (Exception rowEx){
                    failed++;
                    log.debug("Upload {} row {} failed: {}", upload.getId(), rowNumber, rowEx.getMessage());
                }
            }
        }catch (IOException | CsvValidationException e){
            log.error("Upload {} could not be parsed: {}", upload.getId(), e.getMessage());
            throw new BusinessException(ErrorCode.UPLOAD_E003);
        }

        upload.setTotalRecords(processed+failed);
        upload.setProcessedRecords(processed);
        upload.setFailedRecords(failed);
        upload.setStatus(UploadStatus.COMPLETED);
        upload = transactionUploadRepository.save(upload);

        log.info("Upload {} completed: total={}, processed={}, failed={}",
                upload.getId(), upload.getTotalRecords(), upload.getProcessedRecords(), upload.getFailedRecords());

        return toResponse(upload);
    }

    @Override
    public TransactionUploadResponse getUploadById(UUID id) {
        TransactionUpload upload = transactionUploadRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.GEN_E001));
        return toResponse(upload);
    }

    @Override
    public Page<TransactionUploadResponse> getAllUploads(Pageable pageable) {
        return transactionUploadRepository.findAll(pageable).map(this::toResponse);
    }
}
