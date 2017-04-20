DROP DATABASE IF EXISTS recurrent;
CREATE DATABASE recurrent;

USE recurrent;
CREATE TABLE lenders (
	username VARCHAR(45) NOT NULL,
    password1 VARCHAR(45) NOT NULL,
    image VARCHAR(256) NOT NULL,
    email VARCHAR(45) NOT NULL,
    Primary Key (username)
);

CREATE TABLE renters (
	username VARCHAR(45) NOT NULL,
    password1 VARCHAR(45) NOT NULL,
    image VARCHAR(256) NOT NULL,
	email VARCHAR(45) NOT NULL,
    Primary Key (username)
);

CREATE TABLE items (
	id INT AUTO_INCREMENT,
	lender VARCHAR(45) NOT NULL,
    renter VARCHAR(45),
	title VARCHAR(45) NOT NULL,
    image VARCHAR(256) NOT NULL,
    startdate DATE NOT NULL,
    enddate DATE NOT NULL,
    description VARCHAR(256) NOT NULL,
    price double NOT NULL,
    xcoord double NOT NULL,
    ycoord double NOT NULL,
    Primary Key (id)
);

CREATE TABLE messages (
	id INT auto_increment,
	sender VARCHAR(45) NOT NULL,
    receiver VARCHAR(45) NOT NULL,
    title VARCHAR (256) NOT NULL,
    message VARCHAR(256) NOT NULL,
    unread BOOL NOT NULL,
    date DATE NOT NULL,
    Primary Key (id)
)