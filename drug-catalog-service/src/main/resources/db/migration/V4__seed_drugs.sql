INSERT INTO drug_catalog.drugs (name, generic_name, category, dosage_form, strength, status)
VALUES
  ('Crocin', 'Paracetamol', 'Analgesic', 'Tablet', '500mg', 'ACTIVE'),
  ('Augmentin', 'Amoxicillin/Clavulanate', 'Antibiotic', 'Tablet', '625mg', 'ACTIVE'),
  ('Brufen', 'Ibuprofen', 'NSAID', 'Tablet', '400mg', 'ACTIVE'),
  ('Glycomet', 'Metformin', 'Antidiabetic', 'Tablet', '850mg', 'ACTIVE'),
  ('Stugeron', 'Cinnarizine', 'Antihistamine', 'Tablet', '25mg', 'ACTIVE'),
  ('Pantodac', 'Pantoprazole', 'Proton Pump Inhibitor', 'Tablet', '40mg', 'ACTIVE'),
  ('Asthalin', 'Salbutamol', 'Bronchodilator', 'Inhaler', '100mcg', 'ACTIVE'),
  ('Atorva', 'Atorvastatin', 'Statin', 'Tablet', '10mg', 'ACTIVE')
ON CONFLICT DO NOTHING;
