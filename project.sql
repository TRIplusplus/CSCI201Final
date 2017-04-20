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

insert into lenders(username, password1, image, email)
	values ('Bill Gates','b','https://addicted2success.com/wp-content/uploads/2014/11/Bill-Gates-Quotes.jpg','bg@usc.edu');
insert into lenders(username, password1, image, email)
	values ('Raj Koothrappali','r','https://userscontent2.emaze.com/images/f9f89b2d-11a9-4d7f-af08-ae68781c2a55/56dad7d669ca736636d6ce1090b72cc8.jpeg','rk@usc.edu');
insert into lenders(username, password1, image, email)
	values ('Max Nikias','r','https://about.usc.edu/files/2011/07/Nikias.jpg','mn@usc.edu');


CREATE TABLE renters (
	username VARCHAR(45) NOT NULL,
    password1 VARCHAR(45) NOT NULL,
    image VARCHAR(256) NOT NULL,
	email VARCHAR(45) NOT NULL,
    Primary Key (username)
);
insert into renters(username, password1, image, email)
	values ('Jmiller','j','http://www-scf.usc.edu/~csci201/images/jeffrey_miller.jpg','cn@usc.edu');


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
);


-- CREATE TABLE items (
-- 	id INT AUTO_INCREMENT,
-- 	lender VARCHAR(45) NOT NULL,
--     renter VARCHAR(45),
-- 	title VARCHAR(45) NOT NULL,
--     image VARCHAR(256) NOT NULL,
--     startdate DATE NOT NULL,
--     enddate DATE NOT NULL,
--     description VARCHAR(256) NOT NULL
--     price double NOT NULL,
--     xcoord double NOT NULL,
--     ycoord double NOT NULL,
--     Primary Key (id
-- );
-- ti
insert into items(lender,title,image,startdate,enddate,description,price,xcoord,ycoord)
	 values ('Bill Gates', 'Chains', 'https://images-na.ssl-images-amazon.com/images/I/7120XzgcmsL._SL1001_.jpg','2017-01-01','2017-02-02','Anti-slip chains','60.0','47.9062095','-122.5320708');
insert into items(lender,title,image,startdate,enddate,description,price,xcoord,ycoord)
	 values ('Bill Gates', 'Skis', 'http://images.evo.com/imgp/700/103490/455299/burton-clash-snowboard-2017-139.jpg','2017-01-01','2017-02-02','Pro Ski boards','80.0','47.6101497','-122.2015159');
insert into items(lender,title,image,startdate,enddate,description,price,xcoord,ycoord)
	values ('Bill Gates', 'yacht', 'https://www.moranyachts.com/sites/default/files/Clifford_II.jpg','2017-02-01','2017-03-02','Yacht for rent','8880.0','34.0259216','-118.7797571');
insert into items(lender,title,image,startdate,enddate,description,price,xcoord,ycoord)
	values ('Bill Gates', 'Horse', 'https://media.licdn.com/mpr/mpr/shrinknp_800_800/AAEAAQAAAAAAAAkgAAAAJDU4YmI3Y2JiLWNmMmItNDM4MC05NmEyLTQyY2JhODBkOTdkYQ.jpg','2017-02-01','2017-03-02','Horse for win','20000','22.396428','114.109497');
insert into items(lender,title,image,startdate,enddate,description,price,xcoord,ycoord)
	values ('Raj Koothrappali', 'helmet Camera', 'https://shop.gopro.com/on/demandware.static/-/Sites-gopro-products/default/dwb1b30684/hero5launch/Hero5-Black-Carousel-3.png','2017-03-01','2017-04-02','4k camera','50.0','34.1477849','-118.1445155');
insert into items(lender,title,image,startdate,enddate,description,price,xcoord,ycoord)
	values ('Max Nikias', 'goggles', 'https://images-na.ssl-images-amazon.com/images/I/7160Pkml00L._SL1000_.jpg','2017-01-01','2017-02-02','goggles','190.0','34.0223519','-118.285117');

