DROP DATABASE IF EXISTS recurrent;
CREATE DATABASE recurrent;

USE recurrent;
CREATE TABLE recurrent.lenders (
	username VARCHAR(45) NOT NULL,
    password1 VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    Primary Key (username)
);

CREATE TABLE recurrent.renters (
	username VARCHAR(45) NOT NULL,
    password1 VARCHAR(45) NOT NULL,
	email VARCHAR(45) NOT NULL,
    Primary Key (username)
);



