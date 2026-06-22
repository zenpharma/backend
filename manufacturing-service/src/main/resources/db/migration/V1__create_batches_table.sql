CREATE SCHEMA IF NOT EXISTS manufacturing;

CREATE TABLE IF NOT EXISTS manufacturing.batches (
    id             BIGSERIAL    PRIMARY KEY,
    batch_number   VARCHAR(50)  NOT NULL UNIQUE,
    product_name   VARCHAR(255) NOT NULL,
    quantity       INTEGER,
    scheduled_date DATE,
    assigned_line  VARCHAR(50),
    status         VARCHAR(20)  NOT NULL DEFAULT 'SCHEDULED',
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_batches_status ON manufacturing.batches(status);
CREATE INDEX idx_batches_line   ON manufacturing.batches(assigned_line);
