DROP TABLE IF EXISTS card CASCADE;
DROP TABLE IF EXISTS bill CASCADE;
DROP TABLE IF EXISTS atm CASCADE;
DROP TABLE IF EXISTS bank CASCADE;
DROP TABLE IF EXISTS client CASCADE;


CREATE TABLE client
(
    client_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name      VARCHAR(100),
    surname   VARCHAR(100)
);

CREATE TABLE bank
(
    bank_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name    VARCHAR(100)
);

CREATE TABLE bill
(
    bill_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    rub     INTEGER,
    penny   INTEGER,
    bank_id INTEGER,
    FOREIGN KEY (bank_id) REFERENCES bank (bank_id)
);

CREATE TABLE card
(
    card_id     INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    card_number VARCHAR(30),
    client_id   INTEGER,
    pin         INTEGER,
    bill_id     INTEGER,
    FOREIGN KEY (client_id) REFERENCES client (client_id) ON DELETE CASCADE,
    FOREIGN KEY (bill_id) REFERENCES bill (bill_id) ON DELETE CASCADE
);


CREATE table atm
(
    atm_id    INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    bank_id   INTEGER,
    money100  INTEGER,
    money200  INTEGER,
    money500  INTEGER,
    money1000 INTEGER,
    money2000 INTEGER,
    money5000 INTEGER,
    FOREIGN KEY (bank_id) references bank (bank_id)
);


INSERT into  client (name,surname)
values ('Eugen','Muravjov'),
       ('Dmitri','Bukin'),
       ('Andrew','Nowas'),
       ('Dmitri','Loev');

INSERT into  bank (name)
values ('Sber'),
       ('Alfa'),
       ('Tinkoff');


INSERT into  bill (rub, penny, bank_id)
values ( 1500,0,1),
       (50000,50,1),
       (2500,0,1),
       (4000,0,1),
       (2000,0,2),
       (70000,0,2),
       (35000,70,2),
       (6000,0,3),
       (7000,40,3);

INSERT into atm ( money100, money200, money500, money1000, money2000, money5000, bank_id)
VALUES (50,50,100,50,50,100,1),
       (100,100,100,20,50,100,2);

INSERT into card (card_number, client_id, pin, bill_id)
values ('0412 1234 5678 3232',1,1234,1),
       ('4545 5656 6767 7878',2, 3232,2),
       ('1212 3232 2222 1111',3,1632,3),
       ('6666 6666 6666 6661',4,4565,4),
       ('7777 1234 4321 6666',1,4343,5);

