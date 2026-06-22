CREATE SCHEMA IF NOT EXISTS inventory;

CREATE TABLE IF NOT EXISTS inventory.inventory_items (
    id            BIGSERIAL PRIMARY KEY,
    item_code     VARCHAR(50)  NOT NULL UNIQUE,
    item_name     VARCHAR(255) NOT NULL,
    item_type     VARCHAR(20)  NOT NULL,
    quantity      INTEGER      NOT NULL DEFAULT 0,
    unit_of_measure VARCHAR(50),
    status        VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_inventory_status   ON inventory.inventory_items(status);
CREATE INDEX idx_inventory_itemtype ON inventory.inventory_items(item_type);
