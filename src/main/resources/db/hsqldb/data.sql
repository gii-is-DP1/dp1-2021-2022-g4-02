-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (1,'admin','admin','admin1','4dm1n',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (1,'admin',1);

-- One player user
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (2,'prueba','prueba','player1','123',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (8,'player',2);
INSERT INTO players(id,user_id) VALUES (1,2);
-- One player user
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (3,'prueba','prueba','player2','123',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (9,'player',3);
INSERT INTO players(id,user_id) VALUES (2,3);
-- Usuario de Dámaris
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (4,'admin','admin','damgomser','damgomser',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (2,'admin',4);
-- Usuario de Miguel
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (5,'admin','admin','migrivros','migrivros',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (3,'admin',5);
-- Usuario de Jose
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (6,'admin','admin','josmarluq','josmarluq',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (4,'admin',6);
-- Usuario de Iván
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (7,'admin','admin','ivamorgra','passw2',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (5,'admin',7);
-- Usuario de Antonio
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (8,'admin','admin','antlopcub','antlopcub',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (6,'admin',8);
-- Usuario de Rafa
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (9,'admin','admin','rafsanesp','rafsanesp',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (7,'admin',9);

--INSERT INTO vets VALUES (1, 'James', 'Carter');
--INSERT INTO vets VALUES (2, 'Helen', 'Leary');
--INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
--INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
--INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
--INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');
--
--INSERT INTO specialties VALUES (1, 'radiology');
--INSERT INTO specialties VALUES (2, 'surgery');
--INSERT INTO specialties VALUES (3, 'dentistry');
--
--INSERT INTO vet_specialties VALUES (2, 1);
--INSERT INTO vet_specialties VALUES (3, 2);
--INSERT INTO vet_specialties VALUES (3, 3);
--INSERT INTO vet_specialties VALUES (4, 2);
--INSERT INTO vet_specialties VALUES (5, 1);
--
--INSERT INTO types VALUES (1, 'cat');
--INSERT INTO types VALUES (2, 'dog');
--INSERT INTO types VALUES (3, 'lizard');
--INSERT INTO types VALUES (4, 'snake');
--INSERT INTO types VALUES (5, 'bird');
--INSERT INTO types VALUES (6, 'hamster');
--INSERT INTO types VALUES (7, 'turtle');

--INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
--INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
--INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
--INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
--INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
--INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
--INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
--INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
--INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
--INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

---- Datos de la dueña Dámaris
--INSERT INTO owners VALUES (11, 'Damaris', 'Gomez Serrano', 'Calle Mercurio', 'Rota', '627334766', 'damgomser');
---- Datos del dueño Miguel
--INSERT INTO owners VALUES (12, 'Miguel Ángel', 'Rivas Rosado', 'Calle José de Soto y Molina', 'Jerez de la Frontera', '636947113', 'migrivros');
---- Datos del dueño Jose
--INSERT INTO owners VALUES (13, 'Jose Manuel', 'Martin Luque', 'Calle Mendez Nuñez', 'Marchena', '615218018', 'josmarluq');
---- Datos del dueño Iván
--INSERT INTO owners VALUES (14,'Ivan','Moreno Granado','Calle Belmonte','Guillena','608299562','ivamorgra');
---- Datos del dueño Antonio
--INSERT INTO owners VALUES (15,'Antonio Jose','Lopez Cubiles','Calle Virgen de Guia','Castilleja de la Cuesta','640605765','antlopcub');
---- Datos del dueño Rafa
--INSERT INTO owners VALUES (16,'Rafael','Sanabria Esparrago','Calle Cardenal Ilundain','Sevilla','674907182','rafsanesp');
--
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

---- Mascota de Dámaris
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (14, 'Simba', '2018-03-14', 2, 11);
---- Mascota de Miguel
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (15, 'Scooby', '2019-01-20', 2, 12);
---- Mascota de Jose
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (16, 'Ringo', '2012-01-04', 2, 13);
---- Mascota(s) de Iván
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (17,'Lola','2009-09-12',7,14);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (18,'Budy','2019-01-25',2,14);
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (19,'Coco','2021-09-25',5,14);
---- Mascota de Antonio
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (20,'Limón','2021-07-7',1,15);
---- Mascota de Rafa
--INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (21,'Aguacate','2014-01-7',4,16);
--
--
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
--INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');


---Doblones
INSERT INTO cards(id, card_type) VALUES (1, 0);
INSERT INTO cards(id, card_type) VALUES (2, 0);
INSERT INTO cards(id, card_type) VALUES (3, 0);
INSERT INTO cards(id, card_type) VALUES (4, 0);
INSERT INTO cards(id, card_type) VALUES (5, 0);
INSERT INTO cards(id, card_type) VALUES (6, 0);
INSERT INTO cards(id, card_type) VALUES (7, 0);
INSERT INTO cards(id, card_type) VALUES (8, 0);
INSERT INTO cards(id, card_type) VALUES (9, 0);
INSERT INTO cards(id, card_type) VALUES (10, 0);
INSERT INTO cards(id, card_type) VALUES (11, 0);
INSERT INTO cards(id, card_type) VALUES (12, 0);
INSERT INTO cards(id, card_type) VALUES (13, 0);
INSERT INTO cards(id, card_type) VALUES (14, 0);
INSERT INTO cards(id, card_type) VALUES (15, 0);
INSERT INTO cards(id, card_type) VALUES (16, 0);
INSERT INTO cards(id, card_type) VALUES (17, 0);
INSERT INTO cards(id, card_type) VALUES (18, 0);
INSERT INTO cards(id, card_type) VALUES (19, 0);
INSERT INTO cards(id, card_type) VALUES (20, 0);
INSERT INTO cards(id, card_type) VALUES (21, 0);
INSERT INTO cards(id, card_type) VALUES (22, 0);
INSERT INTO cards(id, card_type) VALUES (23, 0);
INSERT INTO cards(id, card_type) VALUES (24, 0);
INSERT INTO cards(id, card_type) VALUES (25, 0);
INSERT INTO cards(id, card_type) VALUES (26, 0);
INSERT INTO cards(id, card_type) VALUES (27, 0);
---Calices
INSERT INTO cards(id, card_type) VALUES (28, 1);
INSERT INTO cards(id, card_type) VALUES (29, 1);
INSERT INTO cards(id, card_type) VALUES (30, 1);
---Rubíes
INSERT INTO cards(id, card_type) VALUES (31, 2);
INSERT INTO cards(id, card_type) VALUES (32, 2);
INSERT INTO cards(id, card_type) VALUES (33, 2);
---Diamantes
INSERT INTO cards(id, card_type) VALUES (34, 3);
INSERT INTO cards(id, card_type) VALUES (35, 3);
INSERT INTO cards(id, card_type) VALUES (36, 3);
---Collares
INSERT INTO cards(id, card_type) VALUES (37, 4);
INSERT INTO cards(id, card_type) VALUES (38, 4);
INSERT INTO cards(id, card_type) VALUES (39, 4);
INSERT INTO cards(id, card_type) VALUES (40, 4);
---Mapas
INSERT INTO cards(id, card_type) VALUES (41, 5);
INSERT INTO cards(id, card_type) VALUES (42, 5);
INSERT INTO cards(id, card_type) VALUES (43, 5);
INSERT INTO cards(id, card_type) VALUES (44, 5);
---Coronas
INSERT INTO cards(id, card_type) VALUES (45, 6);
INSERT INTO cards(id, card_type) VALUES (46, 6);
INSERT INTO cards(id, card_type) VALUES (47, 6);
INSERT INTO cards(id, card_type) VALUES (48, 6);
---Revólveres
INSERT INTO cards(id, card_type) VALUES (49, 7);
INSERT INTO cards(id, card_type) VALUES (50, 7);
INSERT INTO cards(id, card_type) VALUES (51, 7);
INSERT INTO cards(id, card_type) VALUES (52, 7);
INSERT INTO cards(id, card_type) VALUES (53, 7);
INSERT INTO cards(id, card_type) VALUES (54, 7);
---Espadas
INSERT INTO cards(id, card_type) VALUES (55, 8);
INSERT INTO cards(id, card_type) VALUES (56, 8);
INSERT INTO cards(id, card_type) VALUES (57, 8);
INSERT INTO cards(id, card_type) VALUES (58, 8);
INSERT INTO cards(id, card_type) VALUES (59, 8);
INSERT INTO cards(id, card_type) VALUES (60, 8);
---Ron
INSERT INTO cards(id, card_type) VALUES (61, 9);
INSERT INTO cards(id, card_type) VALUES (62, 9);
INSERT INTO cards(id, card_type) VALUES (63, 9);
INSERT INTO cards(id, card_type) VALUES (64, 9);
INSERT INTO cards(id, card_type) VALUES (65, 9);
INSERT INTO cards(id, card_type) VALUES (66, 9);

-- Islas
INSERT INTO isles(id, card_id) VALUES (1, null);
INSERT INTO isles(id, card_id) VALUES (2, null);
INSERT INTO isles(id, card_id) VALUES (3, null);
INSERT INTO isles(id, card_id) VALUES (4, null);
INSERT INTO isles(id, card_id) VALUES (5, null);
INSERT INTO isles(id, card_id) VALUES (6, null);


-- Creación de partidas en curso        
insert into games(id, code, current_player, start_hour, end_hour) values (1,'3',1,'7:30', '7:50');