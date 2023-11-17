INSERT IGNORE INTO gender (gender) VALUES ('Male');
INSERT IGNORE INTO gender (gender) VALUES ('Female');
INSERT IGNORE INTO gender (gender) VALUES ('Not known');
INSERT IGNORE INTO gender (gender) VALUES ('Not applicable');

INSERT IGNORE INTO address (id, number, street) VALUES
(1, '1', 'Brookside St'),
(2, '2','High St'),
(3, '3','Club Road'),
(4, '4','Valley Dr');


INSERT IGNORE INTO patient (first_name, last_name, date_of_birth, gender_id, address_id, phone_number) VALUES
('TestNone', 'Test', '1966-12-31', 1, 1, '100-222-3333'),
('TestBorderline', 'Test', '1945-06-24', 2, 2, '200-333-4444'),
('TestInDanger', 'Test', '2004-06-18', 2, 3, '300-444-5555'),
('TestEarlyOnsetTest', 'Test', '2002-06-28', 1, 4, '400-555-6666');