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
INSERT INTO cards(id, card_type, url_card_img) VALUES (1, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (2, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (3, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (4, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (5, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (6, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (7, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (8, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (9, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (10, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (11, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (12, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (13, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (14, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (15, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (16, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (17, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (18, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (19, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (20, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (21, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (22, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (23, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (24, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (25, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (26, 0, '/resources/images/doblon.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (27, 0, '/resources/images/doblon.jpg');
---Calices
INSERT INTO cards(id, card_type, url_card_img) VALUES (28, 1, '/resources/images/caliz.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (29, 1, '/resources/images/caliz.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (30, 1, '/resources/images/caliz.jpg');
---Rubíes
INSERT INTO cards(id, card_type, url_card_img) VALUES (31, 2, '/resources/images/rubi.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (32, 2, '/resources/images/rubi.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (33, 2, '/resources/images/rubi.jpg');
---Diamantes
INSERT INTO cards(id, card_type, url_card_img) VALUES (34, 3, '/resources/images/diamante.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (35, 3, '/resources/images/diamante.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (36, 3, '/resources/images/diamante.jpg');
---Collares
INSERT INTO cards(id, card_type, url_card_img) VALUES (37, 4, '/resources/images/collar.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (38, 4, '/resources/images/collar.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (39, 4, '/resources/images/collar.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (40, 4, '/resources/images/collar.jpg');
---Mapas
INSERT INTO cards(id, card_type, url_card_img) VALUES (41, 5, '/resources/images/mapa.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (42, 5, '/resources/images/mapa.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (43, 5, '/resources/images/mapa.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (44, 5, '/resources/images/mapa.jpg');
---Coronas
INSERT INTO cards(id, card_type, url_card_img) VALUES (45, 6, '/resources/images/corona.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (46, 6, '/resources/images/corona.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (47, 6, '/resources/images/corona.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (48, 6, '/resources/images/corona.jpg');
---Pistolas
INSERT INTO cards(id, card_type, url_card_img) VALUES (49, 7, '/resources/images/pistola.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (50, 7, '/resources/images/pistola.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (51, 7, '/resources/images/pistola.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (52, 7, '/resources/images/pistola.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (53, 7, '/resources/images/pistola.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (54, 7, '/resources/images/pistola.jpg');
---Espadas
INSERT INTO cards(id, card_type, url_card_img) VALUES (55, 8, '/resources/images/espada.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (56, 8, '/resources/images/espada.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (57, 8, '/resources/images/espada.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (58, 8, '/resources/images/espada.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (59, 8, '/resources/images/espada.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (60, 8, '/resources/images/espada.jpg');
---Ron
INSERT INTO cards(id, card_type, url_card_img) VALUES (61, 9, '/resources/images/ron.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (62, 9, '/resources/images/ron.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (63, 9, '/resources/images/ron.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (64, 9, '/resources/images/ron.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (65, 9, '/resources/images/ron.jpg');
INSERT INTO cards(id, card_type, url_card_img) VALUES (66, 9, '/resources/images/ron.jpg');

-- Islas
INSERT INTO isles(id) VALUES (1);
INSERT INTO isles(id) VALUES (2);
INSERT INTO isles(id) VALUES (3);
INSERT INTO isles(id) VALUES (4);
INSERT INTO isles(id) VALUES (5);
INSERT INTO isles(id) VALUES (6);

--Juego Terminado
INSERT INTO games(id,code,current_player,current_round,end_hour,finished_turn,initial_player,max_rounds,start_hour,game_mode) VALUES (1, 'N0IAUT',3,12,null,0,0,12,'14:49:33',1);

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



INSERT INTO statistics(id, player_id, games_played, games_won, average_score, average_time, total_score, total_time, doubloon_count, chalice_count, diamond_count, ruby_count, rum_count, gun_count, necklace_count, sword_count, map_count, crown_count) VALUES (1, 1, 0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO statistics(id, player_id, games_played, games_won, average_score, average_time, total_score, total_time, doubloon_count, chalice_count, diamond_count, ruby_count, rum_count, gun_count, necklace_count, sword_count, map_count, crown_count) VALUES (2, 2, 0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO statistics(id, player_id, games_played, games_won, average_score, average_time, total_score, total_time, doubloon_count, chalice_count, diamond_count, ruby_count, rum_count, gun_count, necklace_count, sword_count, map_count, crown_count) VALUES (3, 3, 0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO statistics(id, player_id, games_played, games_won, average_score, average_time, total_score, total_time, doubloon_count, chalice_count, diamond_count, ruby_count, rum_count, gun_count, necklace_count, sword_count, map_count, crown_count) VALUES (4, 4, 0, 0, 0.0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);


INSERT INTO achievements(id, achievement_type) VALUES (1, 0);
INSERT INTO achievements(id, achievement_type) VALUES (2, 1);
INSERT INTO achievements(id, achievement_type) VALUES (3, 2);
INSERT INTO achievements(id, achievement_type) VALUES (4, 3);
INSERT INTO achievements(id, achievement_type) VALUES (5, 4);
INSERT INTO achievements(id, achievement_type) VALUES (6, 5);
INSERT INTO achievements(id, achievement_type) VALUES (7, 6);
INSERT INTO achievements(id, achievement_type) VALUES (8, 7);
INSERT INTO achievements(id, achievement_type) VALUES (9, 8);
INSERT INTO achievements(id, achievement_type) VALUES (10, 9);
INSERT INTO achievements(id, achievement_type) VALUES (11, 10);
INSERT INTO achievements(id, achievement_type) VALUES (12, 11);
INSERT INTO achievements(id, achievement_type) VALUES (13, 12);
INSERT INTO achievements(id, achievement_type) VALUES (14, 13);
INSERT INTO achievements(id, achievement_type) VALUES (15, 14);
INSERT INTO achievements(id, achievement_type) VALUES (16, 15);
INSERT INTO achievements(id, achievement_type) VALUES (17, 16);
INSERT INTO achievements(id, achievement_type) VALUES (18, 17);
INSERT INTO achievements(id, achievement_type) VALUES (19, 18);
INSERT INTO achievements(id, achievement_type) VALUES (20, 19);
INSERT INTO achievements(id, achievement_type) VALUES (21, 20);
INSERT INTO achievements(id, achievement_type) VALUES (22, 21);
INSERT INTO achievements(id, achievement_type) VALUES (23, 22);
INSERT INTO achievements(id, achievement_type) VALUES (24, 23);

--Player 1
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,1,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,2,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,3,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,4,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,5,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,6,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,7,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,8,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,9,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,10,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,11,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,12,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,13,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,14,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,15,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,16,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,17,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,18,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,19,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,20,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,21,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,22,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,23,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (1,24,false);

--Player2 
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,1,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,2,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,3,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,4,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,5,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,6,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,7,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,8,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,9,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,10,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,11,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,12,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,13,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,14,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,15,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,16,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,17,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,18,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,19,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,20,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,21,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,22,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,23,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (2,24,false);

--Player3
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,1,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,2,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,3,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,4,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,5,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,6,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,7,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,8,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,9,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,10,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,11,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,12,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,13,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,14,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,15,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,16,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,17,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,18,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,19,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,20,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,21,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,22,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,23,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (3,24,false);

--Player4
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,1,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,2,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,3,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,4,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,5,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,6,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,7,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,8,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,9,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,10,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,11,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,12,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,13,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,14,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,15,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,16,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,17,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,18,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,19,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,20,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,21,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,22,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,23,false);
INSERT INTO achievement_statuses(statistics_id, achievement_id,achieved) VALUES (4,24,false);