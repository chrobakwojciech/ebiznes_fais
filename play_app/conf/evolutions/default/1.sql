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

INSERT INTO [user] VALUES ('1', 'Jan', 'Kowalski', 'jk@gmail.com', 'admin123');
INSERT INTO [user] VALUES ('2', 'Piotr', 'Nowak', 'pn@gmail.com', 'admin123');

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