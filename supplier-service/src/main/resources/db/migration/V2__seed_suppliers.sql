INSERT INTO procurement.suppliers (supplier_code, company_name, contact_person, email, phone, status)
VALUES
  ('SUP-001', 'ChemIndia Pvt Ltd',  'Rajesh Kumar', 'rajesh@chemindia.com',  '+91-9876543210', 'ACTIVE'),
  ('SUP-002', 'PharmaRaw Corp',     'Priya Sharma', 'priya@pharmaraw.com',   '+91-9876543211', 'ACTIVE'),
  ('SUP-003', 'BioSynth Labs',      'Amit Patel',   'amit@biosynth.com',     '+91-9876543212', 'ACTIVE'),
  ('SUP-004', 'Global APIs Ltd',    'Sunita Rao',   'sunita@globalapis.com', '+91-9876543213', 'ACTIVE')
ON CONFLICT DO NOTHING;
