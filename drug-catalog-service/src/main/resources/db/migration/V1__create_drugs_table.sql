CREATE TABLE IF NOT EXISTS drug_catalog.drugs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    genericName VARCHAR(255),
    category VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_drugs_status ON drug_catalog.drugs(status);
