INSERT INTO manufacturing.batches (batch_number, product_name, quantity, scheduled_date, assigned_line, status)
VALUES
  ('BATCH-2024-001', 'Paracetamol 500mg',  10000, '2024-04-10', 'Line A', 'COMPLETED'),
  ('BATCH-2024-002', 'Amoxicillin 250mg',   8000, '2024-04-12', 'Line B', 'COMPLETED'),
  ('BATCH-2024-003', 'Ibuprofen 400mg',     6000, '2024-04-15', 'Line A', 'IN_PROGRESS'),
  ('BATCH-2024-004', 'Metformin 850mg',     5000, '2024-04-18', 'Line C', 'SCHEDULED')
ON CONFLICT DO NOTHING;
