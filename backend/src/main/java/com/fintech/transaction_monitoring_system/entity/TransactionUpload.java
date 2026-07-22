package com.fintech.transaction_monitoring_system.entity;

import com.fintech.transaction_monitoring_system.enums.UploadStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction_uploads")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @Column(nullable = false)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UploadStatus status = UploadStatus.PENDING;

    @Builder.Default
    private int totalRecords = 0;

    @Builder.Default
    private int processedRecords = 0;

    @Builder.Default
    private int failedRecords = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
