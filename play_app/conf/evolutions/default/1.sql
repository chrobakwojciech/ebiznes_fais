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

INSERT INTO actor VALUES ('1', 'Aktor', '1', 'photo url');
INSERT INTO actor VALUES ('2', 'Aktor', '2', 'photo url');
INSERT INTO actor VALUES ('3', 'Aktor', '3', 'photo url');
INSERT INTO actor VALUES ('4', 'Aktor', '4', 'photo url');

INSERT INTO director VALUES ('1', 'Rezyser', '1', 'photo url');
INSERT INTO director VALUES ('2', 'Rezyser', '2', 'photo url');
INSERT INTO director VALUES ('3', 'Rezyser', '3', 'photo url');
INSERT INTO director VALUES ('4', 'Rezyser', '4', 'photo url');

INSERT INTO genre VALUES ('1', 'Komedia');
INSERT INTO genre VALUES ('2', 'Dramat');
INSERT INTO genre VALUES ('3', 'Sci-Fi');
INSERT INTO genre VALUES ('4', 'Dokument');

INSERT INTO movie VALUES ('1', 'Film A', 'Opis filmu A', 11.99, '2010', 'photo url');
INSERT INTO movie VALUES ('2', 'Film B', 'Opis filmu B', 12.99, '2006', 'photo url');
INSERT INTO movie VALUES ('3', 'Film C', 'Opis filmu C', 19.99, '2011', 'photo url');
INSERT INTO movie VALUES ('4', 'Film D', 'Opis filmu D', 9.99, '2011', 'photo url');

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

INSERT INTO movie_actor VALUES ('1', '1');
INSERT INTO movie_actor VALUES ('1', '2');
INSERT INTO movie_actor VALUES ('1', '3');
INSERT INTO movie_actor VALUES ('2', '1');
INSERT INTO movie_actor VALUES ('2', '4');
INSERT INTO movie_actor VALUES ('3', '1');
INSERT INTO movie_actor VALUES ('4', '2');
INSERT INTO movie_actor VALUES ('4', '3');

INSERT INTO movie_director VALUES ('1', '1');
INSERT INTO movie_director VALUES ('2', '4');
INSERT INTO movie_director VALUES ('3', '2');
INSERT INTO movie_director VALUES ('3', '1');
INSERT INTO movie_director VALUES ('4', '3');

INSERT INTO movie_genre VALUES ('1', '1');
INSERT INTO movie_genre VALUES ('2', '4');
INSERT INTO movie_genre VALUES ('3', '2');
INSERT INTO movie_genre VALUES ('4', '1');
INSERT INTO movie_genre VALUES ('4', '3');


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