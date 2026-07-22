package com.fintech.transaction_monitoring_system.repository;

import com.fintech.transaction_monitoring_system.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findByUploadId(UUID uploadId, Pageable pageable);
}
