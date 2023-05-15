-- USE VOTES;

INSERT INTO users (email, first_name, last_name, password)
VALUES ('user@gmail.com', 'User', 'First', '{noop}password'),
       ('admin@gmail.com', 'Admin', 'Second', '{noop}admin'),
       ('newuser@gmail.com', 'User', 'Third', '{noop}newpassword'),
       ('bruce_nolan@gmail.com', 'Bruce', 'Almighty', '{noop}power');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3),
       ('USER', 4),
       ('ADMIN', 4);

INSERT INTO restaurant(name)
VALUES ('London Paradise'),
       ('Moscow Night');

INSERT INTO dish (name, price, create_date, restaurant_id)
VALUES ('Pizza', 100.00, now(), 1),
       ('Cake', 50.90, now(), 1),
       ('Tea', 10.55, now(), 2),
       ('Lasagna', 159.20, now(), 2);

INSERT INTO vote (user_id, restaurant_id, vote_date)
VALUES (4, 1, now()),
       (3, 2, now());
