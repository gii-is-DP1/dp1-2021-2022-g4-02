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
-- One player user
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (10,'prueba','prueba','player3','123',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (10,'player',10);
INSERT INTO players(id,user_id) VALUES (3,10);
-- One player user
INSERT INTO users(id,first_name,last_name,username,password,enabled) VALUES (11,'prueba','prueba','player4','123',TRUE);
INSERT INTO authorities(id,authority,user_id) VALUES (11,'player',11);
INSERT INTO players(id,user_id) VALUES (4,11);

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

--Juego Terminado
INSERT INTO games(id,code,current_player,current_round,end_hour,finished_turn,initial_player,max_rounds,start_hour) VALUES (1, 'N0IAUT',0,11,null,0,0,12,'14:49:33');

INSERT INTO island_status(id, card_id, game_id, island_id) VALUES (1,33,1,1);
INSERT INTO island_status(id, card_id, game_id, island_id) VALUES (2,12,1,2);
INSERT INTO island_status(id, card_id, game_id, island_id) VALUES (3,61,1,3);
INSERT INTO island_status(id, card_id, game_id, island_id) VALUES (4,59,1,4);
INSERT INTO island_status(id, card_id, game_id, island_id) VALUES (5,65,1,5);
INSERT INTO island_status(id, card_id, game_id, island_id) VALUES (6,54,1,6);

INSERT INTO status(id, winner, game_id, player_id) VALUES (1,0,1,1);
INSERT INTO status(id, winner, game_id, player_id) VALUES (2,0,1,2);
INSERT INTO status(id, winner, game_id, player_id) VALUES (3,0,1,3);
INSERT INTO status(id, winner, game_id, player_id) VALUES (4,0,1,4);

INSERT INTO players_status(player_id,status_id) VALUES (1,1);
INSERT INTO players_status(player_id,status_id) VALUES (1,2);
INSERT INTO players_status(player_id,status_id) VALUES (1,3);
INSERT INTO players_status(player_id,status_id) VALUES (1,4);

INSERT INTO games_status(game_id,status_id) VALUES (1,1);
INSERT INTO games_status(game_id,status_id) VALUES (1,2);
INSERT INTO games_status(game_id,status_id) VALUES (1,3);
INSERT INTO games_status(game_id,status_id) VALUES (1,4);

INSERT INTO games_island_status(game_id,island_status_id) VALUES (1,1);
INSERT INTO games_island_status(game_id,island_status_id) VALUES (1,2);
INSERT INTO games_island_status(game_id,island_status_id) VALUES (1,3);
INSERT INTO games_island_status(game_id,island_status_id) VALUES (1,4);
INSERT INTO games_island_status(game_id,island_status_id) VALUES (1,5);
INSERT INTO games_island_status(game_id,island_status_id) VALUES (1,6);

INSERT INTO hand(status_id,cards_id) VALUES (1,1);
INSERT INTO hand(status_id,cards_id) VALUES (1,2);
INSERT INTO hand(status_id,cards_id) VALUES (1,3);
INSERT INTO hand(status_id,cards_id) VALUES (1,24);
INSERT INTO hand(status_id,cards_id) VALUES (1,54);
INSERT INTO hand(status_id,cards_id) VALUES (1,13);
INSERT INTO hand(status_id,cards_id) VALUES (1,14);

INSERT INTO hand(status_id,cards_id) VALUES (2,4);
INSERT INTO hand(status_id,cards_id) VALUES (2,43);
INSERT INTO hand(status_id,cards_id) VALUES (2,62);
INSERT INTO hand(status_id,cards_id) VALUES (2,32);
INSERT INTO hand(status_id,cards_id) VALUES (2,22);

INSERT INTO hand(status_id,cards_id) VALUES (3,37);
INSERT INTO hand(status_id,cards_id) VALUES (3,38);
INSERT INTO hand(status_id,cards_id) VALUES (3,8);
INSERT INTO hand(status_id,cards_id) VALUES (3,49);
INSERT INTO hand(status_id,cards_id) VALUES (3,50);
INSERT INTO hand(status_id,cards_id) VALUES (3,58);
INSERT INTO hand(status_id,cards_id) VALUES (3,19);
INSERT INTO hand(status_id,cards_id) VALUES (3,25);

INSERT INTO hand(status_id,cards_id) VALUES (4,36);
INSERT INTO hand(status_id,cards_id) VALUES (4,30);
INSERT INTO hand(status_id,cards_id) VALUES (4,18);
INSERT INTO hand(status_id,cards_id) VALUES (4,41);
INSERT INTO hand(status_id,cards_id) VALUES (4,64);
INSERT INTO hand(status_id,cards_id) VALUES (4,66);

