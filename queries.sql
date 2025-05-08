--Insert sample data into Customer table:

INSERT INTO Customer (id, name) VALUES (1,'John Doe');

INSERT INTO Customer (id, name) VALUES (2,'Jane Smith');


--Insert sample data into Transaction table:

INSERT INTO Transaction (customerId, amount, date) VALUES (1, 100.0, '2025-01-15');

INSERT INTO Transaction (customerId, amount, date) VALUES (1, 50.0, '2025-02-20');

INSERT INTO Transaction (customerId, amount, date) VALUES (2, 200.0, '2025-01-10');
