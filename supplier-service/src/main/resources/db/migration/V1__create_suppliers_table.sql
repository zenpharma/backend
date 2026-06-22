CREATE SCHEMA IF NOT EXISTS procurement;

CREATE TABLE IF NOT EXISTS procurement.suppliers (
    id             BIGSERIAL    PRIMARY KEY,
    supplier_code  VARCHAR(50)  NOT NULL UNIQUE,
    company_name   VARCHAR(255) NOT NULL,
    contact_person VARCHAR(255),
    email          VARCHAR(255) UNIQUE,
    phone          VARCHAR(50),
    status         VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_suppliers_status ON procurement.suppliers(status);
