ALTER TABLE drug_catalog.drugs RENAME COLUMN "genericname" TO generic_name;
ALTER TABLE drug_catalog.drugs ADD COLUMN IF NOT EXISTS strength VARCHAR(255);
