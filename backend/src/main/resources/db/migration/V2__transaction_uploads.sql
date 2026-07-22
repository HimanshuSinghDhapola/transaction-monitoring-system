CREATE TABLE transaction_uploads(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    uploaded_by     UUID NOT NULL REFERENCES users(id),
    file_name       VARCHAR(255) NOT NULL,
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    total_records   INT NOT NULL DEFAULT 0,
    failed_records  INT NOT NULL DEFAULT 0,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
)