INSERT IGNORE INTO vets VALUES (default, 'James', 'Carter');
INSERT IGNORE INTO vets VALUES (default, 'Helen', 'Leary');
INSERT IGNORE INTO vets VALUES (default, 'Linda', 'Douglas');
INSERT IGNORE INTO vets VALUES (default, 'Rafael', 'Ortega');
INSERT IGNORE INTO vets VALUES (default, 'Henry', 'Stevens');
INSERT IGNORE INTO vets VALUES (default, 'Sharon', 'Jenkins');

INSERT IGNORE INTO specialties VALUES (default, 'radiology');
INSERT IGNORE INTO specialties VALUES (default, 'surgery');
INSERT IGNORE INTO specialties VALUES (default, 'dentistry');

INSERT IGNORE INTO vet_specialties VALUES (2, 1);
INSERT IGNORE INTO vet_specialties VALUES (3, 2);
INSERT IGNORE INTO vet_specialties VALUES (3, 3);
INSERT IGNORE INTO vet_specialties VALUES (4, 2);
INSERT IGNORE INTO vet_specialties VALUES (5, 1);

INSERT IGNORE INTO types VALUES (default, 'cat');
INSERT IGNORE INTO types VALUES (default, 'dog');
INSERT IGNORE INTO types VALUES (default, 'lizard');
INSERT IGNORE INTO types VALUES (default, 'snake');
INSERT IGNORE INTO types VALUES (default, 'bird');
INSERT IGNORE INTO types VALUES (default, 'hamster');

INSERT IGNORE INTO owners VALUES (default, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT IGNORE INTO owners VALUES (default, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT IGNORE INTO owners VALUES (default, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT IGNORE INTO owners VALUES (default, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT IGNORE INTO owners VALUES (default, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT IGNORE INTO owners VALUES (default, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT IGNORE INTO owners VALUES (default, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT IGNORE INTO owners VALUES (default, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT IGNORE INTO owners VALUES (default, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT IGNORE INTO owners VALUES (default, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT IGNORE INTO pets VALUES (default, 'Leo', '2010-09-07', 1, 1);
INSERT IGNORE INTO pets VALUES (default, 'Basil', '2012-08-06', 6, 2);
INSERT IGNORE INTO pets VALUES (default, 'Rosy', '2011-04-17', 2, 3);
INSERT IGNORE INTO pets VALUES (default, 'Jewel', '2010-03-07', 2, 3);
INSERT IGNORE INTO pets VALUES (default, 'Iggy', '2010-11-30', 3, 4);
INSERT IGNORE INTO pets VALUES (default, 'George', '2010-01-20', 4, 5);
INSERT IGNORE INTO pets VALUES (default, 'Samantha', '2012-09-04', 1, 6);
INSERT IGNORE INTO pets VALUES (default, 'Max', '2012-09-04', 1, 6);
INSERT IGNORE INTO pets VALUES (default, 'Lucky', '2011-08-06', 5, 7);
INSERT IGNORE INTO pets VALUES (default, 'Mulligan', '2007-02-24', 2, 8);
INSERT IGNORE INTO pets VALUES (default, 'Freddy', '2010-03-09', 5, 9);
INSERT IGNORE INTO pets VALUES (default, 'Lucky', '2010-06-24', 2, 10);
INSERT IGNORE INTO pets VALUES (default, 'Sly', '2012-06-08', 1, 10);

INSERT IGNORE INTO visits VALUES (default, 7, '2013-01-01', 'rabies shot');
INSERT IGNORE INTO visits VALUES (default, 8, '2013-01-02', 'rabies shot');
INSERT IGNORE INTO visits VALUES (default, 8, '2013-01-03', 'neutered');
INSERT IGNORE INTO visits VALUES (default, 7, '2013-01-04', 'spayed');
