-- USE VOTES;

INSERT INTO USERS (email, first_name, last_name, password)
VALUES ('user@gmail.com', 'User', 'First', '{noop}password'),
       ('admin@gmail.com', 'Admin', 'Second', '{noop}admin'),
       ('newuser@gmail.com', 'User', 'Third', '{noop}newpassword');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

INSERT INTO RESTAURANT(name)
VALUES ('London Paradise'),
       ('Moscow Night');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Pizza', 100.00, 1),
       ('Cake', 50.90, 1),
       ('Tea', 10.55, 2),
       ('Lasagna', 159.20, 2);

-- vote_id=user_id
INSERT INTO VOTE (user_id, restaurant_id, vote_date)
VALUES (2, 1, now()),
       (3, 2, now());
