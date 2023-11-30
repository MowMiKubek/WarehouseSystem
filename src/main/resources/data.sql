INSERT INTO items (brand, name, ean) VALUES ('Rimmel', 'Szminka', '1234567890123');
INSERT INTO items (brand, name, ean) VALUES ('Bourjois', 'Pomadka', '2345678901234');
INSERT INTO items (brand, name, ean) VALUES ('Ecocera', 'Tusz do rzes', '3456789012345');
INSERT INTO items (brand, name, ean) VALUES ('Max Factor', 'Puder', '4567890123456');
INSERT INTO items (brand, name, ean) VALUES ('Semilac', 'Lakier do paznokci', '5678901234567');
INSERT INTO items (brand, name, ean) VALUES ('Claresa', 'Top do paznokci', '6789012345678');
INSERT INTO items (brand, name, ean) VALUES ('Pilaten', 'Maska na twarz', '7890123456789');
INSERT INTO items (brand, name, ean) VALUES ('Kallos', 'Maska do wlosow', '8901234567890');

INSERT INTO users(name, surname, login, password) VALUES('Maciej', 'Maruda', 'mmaruda', '$2a$10$XLDLmPQeD7MMD6IkDnNy7eEuXcqZsSHMnOnJC5y./sW2ppXUPXBlm');
INSERT INTO users(name, surname, login, password) VALUES('Jakub', 'Kowalik', 'jkowalik', '$2a$10$XLDLmPQeD7MMD6IkDnNy7eEuXcqZsSHMnOnJC5y./sW2ppXUPXBlm');

INSERT INTO document_header(nr, sender, client, type) VALUES ('123', 'Kosmetix', 'Joanna Nowak', 'PZ');
INSERT INTO document_header(nr, sender, client, type) VALUES ('124', 'KosmetPOL', 'Julia Kowal', 'PZ');

INSERT INTO document_body(quantity, id_item, id_document) VALUES(3, 3, 1);
INSERT INTO document_body(quantity, id_item, id_document) VALUES(5, 5, 1);
INSERT INTO document_body(quantity, id_item, id_document) VALUES(9, 6, 1);
INSERT INTO document_body(quantity, id_item, id_document) VALUES(2, 3, 2);
INSERT INTO document_body(quantity, id_item, id_document) VALUES(1, 1, 2);

INSERT INTO warehouse(quantity, id_item) VALUES(5, 3);
INSERT INTO warehouse(quantity, id_item) VALUES(5, 5);
INSERT INTO warehouse(quantity, id_item) VALUES(9, 6);
INSERT INTO warehouse(quantity, id_item) VALUES(1, 1);

