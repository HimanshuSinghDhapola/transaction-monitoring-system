create table users(
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username                    VARCHAR(50) NOT NULL UNIQUE,
    email                       VARCHAR(100) NOT NULL UNIQUE,
    password_hash               VARCHAR(255) NOT NULL,
    password_change_required    BOOLEAN NOT NULL DEFAULT TRUE,
    role                        VARCHAR(20) NOT NULL DEFAULT 'ANALYST',
    enabled                     BOOLEAN NOT NULL DEFAULT TRUE,
    created_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);