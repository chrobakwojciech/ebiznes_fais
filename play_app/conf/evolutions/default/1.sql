# --- !Ups

CREATE TABLE "actor" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "firstName" VARCHAR NOT NULL,
    "lastName" VARCHAR NOT NULL,
    "img" VARCHAR NOT NULL
);

CREATE TABLE "comment" (
    "id" VARCHAR NOT NULL PRIMARY KEY ,
    "content" VARCHAR NOT NULL,
    "user" VARCHAR NOT NULL,
    "movie" VARCHAR NOT NULL,
    FOREIGN KEY ("user") REFERENCES [user]("id"),
    FOREIGN KEY ("movie") REFERENCES movie("id")
);

CREATE TABLE "director" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "firstName" VARCHAR NOT NULL,
    "lastName" VARCHAR NOT NULL,
    "img" VARCHAR NOT NULL
);

CREATE TABLE "genre" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "movie" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "title" VARCHAR NOT NULL,
    "description" VARCHAR NOT NULL,
    "price" FLOAT NOT NULL,
    "productionYear" VARCHAR NOT NULL,
    "img" VARCHAR NOT NULL
);

CREATE TABLE "order" (
    "id" VARCHAR NOT NULL PRIMARY KEY ,
    "user" VARCHAR NOT NULL,
    "payment" VARCHAR NOT NULL,
    FOREIGN KEY ("user") REFERENCES [user]("id"),
    FOREIGN KEY ("payment") REFERENCES payment("id")
);

CREATE TABLE "orderItem" (
    "id" VARCHAR NOT NULL PRIMARY KEY ,
    "order" VARCHAR NOT NULL,
    "movie" VARCHAR NOT NULL,
    FOREIGN KEY ("order") REFERENCES [order]("id"),
    FOREIGN KEY ("movie") REFERENCES movie("id")
);

CREATE TABLE "payment" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL,
    "price" FLOAT NOT NULL
);

CREATE TABLE "rating" (
    "id" VARCHAR NOT NULL PRIMARY KEY ,
    "value" INT NOT NULL,
    "user" VARCHAR NOT NULL,
    "movie" VARCHAR NOT NULL,
    FOREIGN KEY ("user") REFERENCES [user]("id"),
    FOREIGN KEY ("movie") REFERENCES movie("id")
);

CREATE TABLE "user" (
    "id" VARCHAR NOT NULL PRIMARY KEY,
    "firstName" VARCHAR NOT NULL,
    "lastName" VARCHAR NOT NULL,
    "email" VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL
);

CREATE TABLE "movie_actor" (
    "movie" VARCHAR NOT NULL,
    "actor" VARCHAR NOT NULL,
    PRIMARY KEY ("movie", "actor"),
    FOREIGN KEY ("movie") REFERENCES movie("id"),
    FOREIGN KEY ("actor") REFERENCES actor("id")
);

CREATE TABLE "movie_director" (
    "movie" VARCHAR NOT NULL,
    "director" VARCHAR NOT NULL,
    PRIMARY KEY ("movie", "director"),
    FOREIGN KEY ("movie") REFERENCES movie("id"),
    FOREIGN KEY ("director") REFERENCES director("id")
);

CREATE TABLE "movie_genre" (
    "movie" VARCHAR NOT NULL,
    "genre" VARCHAR NOT NULL,
    PRIMARY KEY ("movie", "genre"),
    FOREIGN KEY ("movie") REFERENCES movie("id"),
    FOREIGN KEY ("genre") REFERENCES genre("id")
);

INSERT INTO [user] VALUES ('1', 'Jan', 'Kowalski', 'jk@gmail.com', 'admin123');
INSERT INTO [user] VALUES ('2', 'Piotr', 'Nowak', 'pn@gmail.com', 'admin123');

INSERT INTO actor VALUES ('1', 'John', 'Travolta', 'photo url');
INSERT INTO actor VALUES ('2', 'Samuel', 'L. Jackson', 'photo url');
INSERT INTO actor VALUES ('3', 'Bruce', 'Wilis', 'photo url');
INSERT INTO actor VALUES ('4', 'Leonardo', 'Di Caprio', 'photo url');
INSERT INTO actor VALUES ('5', 'Jamie', 'Foxx', 'photo url');
INSERT INTO actor VALUES ('6', 'Christoph', 'Waltz', 'photo url');
INSERT INTO actor VALUES ('7', 'Brad', 'Pitt', 'photo url');
INSERT INTO actor VALUES ('8', 'Eli', 'Roth', 'photo url');
INSERT INTO actor VALUES ('9', 'Christopher ', 'Carley', 'photo url');
INSERT INTO actor VALUES ('10', 'Bee', 'Wang', 'photo url');
INSERT INTO actor VALUES ('11', 'Morgan', 'Freeman', 'photo url');
INSERT INTO actor VALUES ('12', 'Mike', 'Colter', 'photo url');
INSERT INTO actor VALUES ('13', 'Clint', 'Eastwood', 'photo url');
INSERT INTO actor VALUES ('14', 'Kevin', 'Spacey', 'photo url');
INSERT INTO actor VALUES ('15', 'Ben', 'Affleck', 'photo url');
INSERT INTO actor VALUES ('16', 'Rosamund ', 'Pike', 'photo url');
INSERT INTO actor VALUES ('17', 'Tom ', 'Hardy', 'photo url');
INSERT INTO actor VALUES ('18', 'Fionn', 'Whitehead', 'photo url');
INSERT INTO actor VALUES ('19', 'Tom ', 'Glynn-Carney', 'photo url');
INSERT INTO actor VALUES ('20', 'Mark ', 'Rylance', 'photo url');
INSERT INTO actor VALUES ('21', 'Cilian ', 'Murphy', 'photo url');

INSERT INTO director VALUES ('1', 'Quentin', 'Tarantino', 'https://fwcdn.pl/ppo/01/11/111/449997.2.jpg');
INSERT INTO director VALUES ('2', 'Clint', 'Eastwood', 'https://fwcdn.pl/ppo/01/22/122/449655.2.jpg');
INSERT INTO director VALUES ('3', 'David', 'Fincher', 'https://fwcdn.pl/ppo/04/59/10459/450510.2.jpg');
INSERT INTO director VALUES ('4', 'Christopher', 'Nolan', 'https://fwcdn.pl/ppo/08/96/40896/449999.2.jpg');

INSERT INTO genre VALUES ('1', 'Gangsterski');
INSERT INTO genre VALUES ('2', 'Western');
INSERT INTO genre VALUES ('3', 'Wojenny');
INSERT INTO genre VALUES ('4', 'Dramat');
INSERT INTO genre VALUES ('5', 'Kryminał');
INSERT INTO genre VALUES ('6', 'Thriller');
INSERT INTO genre VALUES ('7', 'Sci-Fi');

INSERT INTO movie VALUES ('1', 'Pulp Fiction', 'Opis filmu', 11.99, '1994', 'photo url');
INSERT INTO movie VALUES ('2', 'Django', 'Opis filmu', 12.99, '2012', 'photo url');
INSERT INTO movie VALUES ('3', 'Bękarty Wojny', 'Opis filmu', 19.99, '2009', 'photo url');
INSERT INTO movie VALUES ('4', 'Gran Torino', 'Opis filmu', 13.99, '2008', 'photo url');
INSERT INTO movie VALUES ('5', 'Za wszelką cenę', 'Opis filmu', 11.99, '2004', 'photo url');
INSERT INTO movie VALUES ('6', 'Dobry, zły i brzydki', 'Opis filmu', 6.99, '1966', 'photo url');
INSERT INTO movie VALUES ('7', 'Siedem', 'Opis filmu', 9.99, '1995', 'photo url');
INSERT INTO movie VALUES ('8', 'Zaginiona dziewczyna', 'Opis filmu', 12.99, '2014', 'photo url');
INSERT INTO movie VALUES ('9', 'Incepcja', 'Opis filmu', 17.99, '2010', 'photo url');
INSERT INTO movie VALUES ('10', 'Dunkierka', 'Opis filmu', 19.99, '2017', 'photo url');

INSERT INTO movie_actor VALUES ('1', '1');
INSERT INTO movie_actor VALUES ('1', '2');
INSERT INTO movie_actor VALUES ('1', '3');
INSERT INTO movie_actor VALUES ('2', '2');
INSERT INTO movie_actor VALUES ('2', '4');
INSERT INTO movie_actor VALUES ('2', '5');
INSERT INTO movie_actor VALUES ('2', '6');
INSERT INTO movie_actor VALUES ('3', '6');
INSERT INTO movie_actor VALUES ('3', '7');
INSERT INTO movie_actor VALUES ('3', '8');
INSERT INTO movie_actor VALUES ('4', '9');
INSERT INTO movie_actor VALUES ('4', '10');
INSERT INTO movie_actor VALUES ('4', '13');
INSERT INTO movie_actor VALUES ('5', '11');
INSERT INTO movie_actor VALUES ('5', '12');
INSERT INTO movie_actor VALUES ('5', '13');
INSERT INTO movie_actor VALUES ('6', '13');
INSERT INTO movie_actor VALUES ('7', '11');
INSERT INTO movie_actor VALUES ('7', '7');
INSERT INTO movie_actor VALUES ('7', '14');
INSERT INTO movie_actor VALUES ('8', '15');
INSERT INTO movie_actor VALUES ('8', '16');
INSERT INTO movie_actor VALUES ('9', '4');
INSERT INTO movie_actor VALUES ('9', '17');
INSERT INTO movie_actor VALUES ('10', '18');
INSERT INTO movie_actor VALUES ('10', '19');
INSERT INTO movie_actor VALUES ('10', '20');
INSERT INTO movie_actor VALUES ('10', '21');

INSERT INTO movie_director VALUES ('1', '1');
INSERT INTO movie_director VALUES ('2', '1');
INSERT INTO movie_director VALUES ('3', '1');
INSERT INTO movie_director VALUES ('4', '2');
INSERT INTO movie_director VALUES ('5', '2');
INSERT INTO movie_director VALUES ('6', '2');
INSERT INTO movie_director VALUES ('7', '3');
INSERT INTO movie_director VALUES ('8', '3');
INSERT INTO movie_director VALUES ('9', '4');
INSERT INTO movie_director VALUES ('10', '4');

INSERT INTO movie_genre VALUES ('1', '1');
INSERT INTO movie_genre VALUES ('2', '2');
INSERT INTO movie_genre VALUES ('3', '3');
INSERT INTO movie_genre VALUES ('4', '4');
INSERT INTO movie_genre VALUES ('5', '4');
INSERT INTO movie_genre VALUES ('6', '2');
INSERT INTO movie_genre VALUES ('7', '5');
INSERT INTO movie_genre VALUES ('7', '6');
INSERT INTO movie_genre VALUES ('8', '4');
INSERT INTO movie_genre VALUES ('8', '6');
INSERT INTO movie_genre VALUES ('9', '6');
INSERT INTO movie_genre VALUES ('9', '7');
INSERT INTO movie_genre VALUES ('10', '3');
INSERT INTO movie_genre VALUES ('10', '4');

INSERT INTO payment VALUES ('1', 'Platnosc 1', 0.0);
INSERT INTO payment VALUES ('2', 'Platnosc 2', 5.99);
INSERT INTO payment VALUES ('3', 'Platnosc 3', 8.99);
INSERT INTO payment VALUES ('4', 'Platnosc 4', 10.99);

INSERT INTO comment VALUES ('1', 'Komentarz 1', '1', '1');
INSERT INTO comment VALUES ('2', 'Komentarz 2', '2', '2');
INSERT INTO comment VALUES ('3', 'Komentarz 3', '2', '3');
INSERT INTO comment VALUES ('4', 'Komentarz 4', '1', '4');
INSERT INTO comment VALUES ('5', 'Komentarz 5', '1', '2');
INSERT INTO comment VALUES ('6', 'Komentarz 6', '2', '1');
INSERT INTO comment VALUES ('7', 'Komentarz 7', '1', '3');

INSERT INTO [order] VALUES ('1', '1', '2');
INSERT INTO [order] VALUES ('2', '2', '1');
INSERT INTO [order] VALUES ('3', '1', '3');
INSERT INTO [order] VALUES ('4', '2', '1');
INSERT INTO [order] VALUES ('5', '2', '4');

INSERT INTO orderItem VALUES ('1', '1', '1');
INSERT INTO orderItem VALUES ('2', '1', '2');
INSERT INTO orderItem VALUES ('3', '1', '3');
INSERT INTO orderItem VALUES ('4', '2', '4');
INSERT INTO orderItem VALUES ('5', '2', '3');
INSERT INTO orderItem VALUES ('6', '3', '1');
INSERT INTO orderItem VALUES ('7', '4', '2');
INSERT INTO orderItem VALUES ('8', '4', '3');
INSERT INTO orderItem VALUES ('9', '5', '1');
INSERT INTO orderItem VALUES ('10', '5', '4');

INSERT INTO rating VALUES ('1', '8', '1', '1');
INSERT INTO rating VALUES ('2', '3', '2', '2');
INSERT INTO rating VALUES ('3', '6', '1', '2');
INSERT INTO rating VALUES ('4', '7', '1', '3');
INSERT INTO rating VALUES ('5', '10', '2', '3');

# --- !Downs

DROP TABLE actor;
DROP TABLE comment;
DROP TABLE director;
DROP TABLE genre;
DROP TABLE movie;
DROP TABLE [order];
DROP TABLE orderItem;
DROP TABLE payment;
DROP TABLE rating;
DROP TABLE [user];