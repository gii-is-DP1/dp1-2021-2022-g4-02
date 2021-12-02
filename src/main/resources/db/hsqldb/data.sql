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
INSERT INTO isles(id) VALUES (1);
INSERT INTO isles(id) VALUES (2);
INSERT INTO isles(id) VALUES (3);
INSERT INTO isles(id) VALUES (4);
INSERT INTO isles(id) VALUES (5);
INSERT INTO isles(id) VALUES (6);