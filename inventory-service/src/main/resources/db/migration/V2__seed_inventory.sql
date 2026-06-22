INSERT INTO inventory.inventory_items (item_code, item_name, item_type, quantity, unit_of_measure, status)
VALUES
  ('INV-001', 'Paracetamol 500mg',   'FINISHED_GOOD', 5000, 'Tablets', 'ACTIVE'),
  ('INV-002', 'Amoxicillin 250mg',   'FINISHED_GOOD', 3200, 'Capsules', 'ACTIVE'),
  ('INV-003', 'Ibuprofen API',        'RAW_MATERIAL',   800, 'KG',      'ACTIVE'),
  ('INV-004', 'Metformin 850mg',      'FINISHED_GOOD', 1200, 'Tablets', 'ACTIVE'),
  ('INV-005', 'Cellulose Excipient',  'RAW_MATERIAL',   250, 'KG',      'ACTIVE')
ON CONFLICT DO NOTHING;
