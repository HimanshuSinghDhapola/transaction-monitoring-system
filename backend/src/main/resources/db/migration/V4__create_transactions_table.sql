CREATE TABLE transactions(
    id                      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    upload_id               UUID NOT NULL REFERENCES transaction_uploads(id),
    account_number          VARCHAR(34) NOT NULL,
    transaction_reference   VARCHAR(100) NOT NULL UNIQUE,
    amount                  NUMERIC(19,4) NOT NULL,
    currency                VARCHAR(3) NOT NULL,
    transaction_date        TIMESTAMP NOT NULL,
    merchant_name           VARCHAR(150),
    channel                 VARCHAR(20) NOT NULL DEFAULT 'OTHER',
    location                VARCHAR(150),
    is_flagged              BOOLEAN NOT NULL DEFAULT FALSE,
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_transactions_upload_id ON transactions(upload_id);