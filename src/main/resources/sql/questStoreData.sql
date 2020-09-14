
insert into roles (role) values ('admin');
insert into roles (role) values ('mentor');
insert into roles (role) values ('student');


insert into users (first_name, last_name, role_id, isActive, email, password) values
('Severus', 'Snape', 2, TRUE, 'severus@snape.com', 'severus123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Agnieszka', 'Koszany', 2, TRUE, 'agnieszka.koszany@codecool.com', 'agnieszka123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Marcin', 'Izworski', 2, TRUE, 'marcin.izworski@codecool.com', 'marcin123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Roman', 'Wolny', 3, TRUE, 'roman@wolny.net', 'roman123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Zenek', 'Prosty', 3, TRUE, 'zenek@prosty.com', 'zenek123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Ala', 'Paskuda', 3, TRUE, 'ala@paskuda.com', 'ala123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Franek', 'Kimono', 1, TRUE, 'franek.kimono@codecool.com', 'franek123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Imre', 'Williams', 3, TRUE, 'imre@williams.com', 'imre123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Max', 'Kolanko', 3, FALSE, 'max@kolanko.com', 'max123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Wojtek', 'Tokarz', 3, TRUE, 'wojtek@tokarz.com', 'wojtek123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Tomek', 'Zielonka', 3, FALSE, 'tomek@zielonka.com', 'tomek123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Kuba', 'Waldemar', 3, FALSE, 'kuba@waldemar.com', 'kuba123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Jurek', 'Killer', 3, TRUE, 'jurek@killer.com', 'kuba123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Ola', 'Ziemba', 3, FALSE, 'ola@ziemba.com', 'ola123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Marta', 'Jurkiewicz', 3, FALSE, 'marta@jurkiewicz.com', 'marta123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Emil', 'Bomba', 3, FALSE, 'emil@bomba.com', 'emil123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Dorota', 'Jaworek', 3, TRUE, 'dorota@jaworek.com', 'dorota123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Ewa', 'Piskarska', 3, TRUE, 'ewa@piskarska.com', 'ewa123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Leon', 'Bezdomny', 3, TRUE, 'leon@bezdomny.com', 'leon123');
insert into users (first_name, last_name, role_id, isActive, email, password) values
('Chris', 'Beck', 3, TRUE, 'chris@beck.com', 'chris123');

insert into categories (category) values ('basic');
insert into categories (category) values ('extra');


insert into modules (name) values ('ProgBase');
insert into modules (name) values ('JavaOOP');
insert into modules (name) values ('Web with SQL');
insert into modules (name) values ('Advanced');

insert into mentors (user_id) values (1);
insert into mentors (user_id) values (2);
insert into mentors (user_id) values (3);


insert into mentor_modules (module_id, mentor_id) values (1, 1);
insert into mentor_modules (module_id, mentor_id) values (2, 2);
insert into mentor_modules (module_id, mentor_id) values (3, 3);
insert into mentor_modules (module_id, mentor_id) values (2, 1);
insert into mentor_modules (module_id, mentor_id) values (3, 2);
insert into mentor_modules (module_id, mentor_id) values (4, 3);


insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 1', 'description 1', 10000, 1, 1, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 2', 'description 2', 13000, 1, 1, FALSE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 3', 'description 3', 18000, 1, 3, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 4', 'description 4', 22000, 1, 1, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 5', 'description 5', 25000, 1, 2, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 6', 'description 6', 30000, 1, 1, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 7', 'description 7', 32000, 1, 3, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 8', 'description 8', 35000, 2, 2, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 9', 'description 9', 38000, 2, 1, TRUE);
insert into rewards (name, description, price, category_id, mentor_id, isactive) values ('reward 10', 'description 10', 40000, 2, 3, TRUE);

insert into shared_wallets (reward_id, coins) values (8, 25000);
insert into shared_wallets (reward_id, coins) values (9, 31000);

insert into quest_statuses (status) values ('not started');
insert into quest_statuses (status) values ('in progres');
insert into quest_statuses (status) values ('submitted');
insert into quest_statuses (status) values ('approved');
insert into quest_statuses (status) values ('not approved');

insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 1', 'description 1', 500, 1, 1, 1, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 2', 'description 2', 1500, 2, 2, 1, FALSE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 3', 'description 3', 2000, 3, 3, 1, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 4', 'description 4', 1300, 4, 2, 1, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 5', 'description 5', 3000, 2, 2, 1, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 6', 'description 6', 3500, 1, 3, 2, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 7', 'description 7', 4000, 3, 1, 2, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 8', 'description 8', 2800, 1, 3, 1, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 9', 'description 9', 3200, 2, 1, 1, TRUE);
insert into quests (name, description, coins_to_earn, module_id, mentor_id, category_id, isactive) values ('quest 10', 'description 10', 4200, 4, 2, 2, TRUE);

insert into students (user_id, module_id, wallet) values (4, 2, 5000);
insert into students (user_id, module_id, wallet) values (5, 2, 6500);
insert into students (user_id, module_id, wallet) values (6, 3, 7200);
insert into students (user_id, module_id, wallet) values (8, 2, 8000);
insert into students (user_id, module_id, wallet) values (9, 3, 7100);
insert into students (user_id, module_id, wallet) values (10, 4, 6200);
insert into students (user_id, module_id, wallet) values (11, 2, 4200);
insert into students (user_id, module_id, wallet) values (12, 3, 7000);
insert into students (user_id, module_id, wallet) values (13, 2, 7650);
insert into students (user_id, module_id, wallet) values (14, 3, 8000);
insert into students (user_id, module_id, wallet) values (15, 2, 5500);

insert into students (user_id, module_id, wallet) values (16, 3, 6000);
insert into students (user_id, module_id, wallet) values (17, 2, 5800);
insert into students (user_id, module_id, wallet) values (18, 4, 6150);
insert into students (user_id, module_id, wallet) values (19, 3, 6900);
insert into students (user_id, module_id, wallet) values (20, 4, 8100);

insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (1, 5, 3, 'quest 1 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (4, 4, 3, 'quest 4 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (6, 5, 4, 'quest 6 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (8, 7, 3, 'quest 8 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (10, 11, 4, 'quest 10 submission');

insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (1, 12, 4, 'quest 1 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (2, 13, 4, 'quest 2 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (2, 14, 2, 'quest 2 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (3, 15, 3, 'quest 3 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (10, 16, 4, 'quest 10 submission');

insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (1, 4, 4, 'quest 1 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (2, 4, 4, 'quest 2 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (2, 9, 2, 'quest 2 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (3, 8, 3, 'quest 3 submission');
insert into student_quests (quest_id, student_id, quest_status_id, quest_input_area) values (10, 7, 4, 'quest 10 submission');


insert into order_statuses (status) values ('used');
insert into order_statuses (status) values ('not used');

insert into orders (student_id, reward_id, order_status_id) values (4, 1, 1);
insert into orders (student_id, reward_id, order_status_id) values (6, 3, 2);
insert into orders (student_id, reward_id, order_status_id) values (9, 4, 2);
insert into orders (student_id, reward_id, order_status_id) values (12, 5, 1);
insert into orders (student_id, reward_id, order_status_id) values (15, 4, 2);
insert into orders (student_id, reward_id, order_status_id) values (4, 2, 2);
insert into orders (student_id, reward_id, order_status_id) values (6, 1, 2);
insert into orders (student_id, reward_id, order_status_id) values (6, 4, 2);
insert into orders (student_id, reward_id, order_status_id) values (9, 1, 2);
