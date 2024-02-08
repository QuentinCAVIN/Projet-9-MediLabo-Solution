INSERT IGNORE INTO gender (id, gender) VALUES (1, 'Female');
INSERT IGNORE INTO gender (id, gender) VALUES (2, 'Male');
INSERT IGNORE INTO gender (id, gender) VALUES (3, 'Not known');
INSERT IGNORE INTO gender (id, gender) VALUES (4, 'Not applicable');

INSERT IGNORE INTO address (id, number, street) VALUES
(1, '1', 'Brookside St'),
(2, '2','High St'),
(3, '3','Club Road'),
(4, '4','Valley Dr');


INSERT IGNORE INTO patient (id, first_name, last_name, date_of_birth, gender_id, address_id, phone_number) VALUES
(1, 'Test', 'TestNone', '1966-12-31', 1, 1, '100-222-3333'),
(2, 'Test', 'TestBorderline', '1945-06-24', 2, 2, '200-333-4444'),
(3, 'Test', 'TestInDanger', '2004-06-18', 2, 3, '300-444-5555'),
(4, 'Test', 'TestEarlyOnsetTest', '2002-06-28', 1, 4, '400-555-6666');