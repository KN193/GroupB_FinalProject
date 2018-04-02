CREATE database serviceechange;

use serviceechange;

CREATE TABLE User (
  username     VARCHAR(30),
  password     VARCHAR(255),
  usertype     VARCHAR(10) NOT NULL  DEFAULT 'ROLE_user',
  nickname     VARCHAR(30) UNIQUE,
  firstname    VARCHAR(20) NOT NULL,
  lastname     VARCHAR(20) NOT NULL,
  gender       CHAR(1) NOT NULL DEFAULT  'M',
  verified     CHAR        NOT NULL  DEFAULT 'N',
  mobile       VARCHAR(10),
  Nationality  VARCHAR(20),
  PreferNation VARCHAR(20),
  DateOfBirth  DATE,
  photo        VARCHAR(100),
  creditLevel  INT         NOT NULL  DEFAULT 5,
  virtualMoney DOUBLE      NOT NULL  DEFAULT 100,
  CONSTRAINT user_pk PRIMARY KEY (username),
  CONSTRAINT Ver_check CHECK (verified = 'Y' OR verified = 'N'),
  CONSTRAINT type_const CHECK (usertype = 'ROLE_user' OR usertype = 'ROLE_admin'),
  CONSTRAINT genderCheck CHECK (gender ='M' or gender ='F')
);

-insert into User VALUES ('xz906@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','ROLE_admin','Rainy','Xinyu','Zhang','M','Y','0476622798','Chinese','Chinese','1992-07-10','',5,'100');
 -insert into User(username, password, nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('jz491@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','Frank','Jianbo','Zhao','M','Y','Chinese','Chinese');
 -insert into User(username, password, nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('jr239@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','End','Junxin','Ren','M','Y','Chinese','Chinese');
 -insert into User(username, password, nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('qc851@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','Tim','Qiusheng','Chu','M','Y','Chinese','Chinese');
 -insert into User(username, password, nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('adk829@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','Asnwin','Asnwin','unknown','M','Y','Indian','Indian');
 -insert into User(username, password, nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('svm636@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','Sameer','Sameer','unknown','M','Y','Saudi Arabia','Saudi Arabia');
 -insert into User(username, password, nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('wx432@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','Owen','Wenqiang','xu','M','Y','Singapore','Singapore');
 -insert into User(username, password, nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('saan977@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','Ali','unknown','unknown','M','Y','unknown','unknown');
 -insert into User(username, password,usertype,nickname, firstname, lastname,gender,verified,Nationality, PreferNation)
 -         VALUES ('hkn193@uowmail.edu.au','$2a$10$yJ8EJIf0ezj5uSC0XhyTYuB83o4LsTPml7TfQ8cuE01u/2BBNuf1q','ROLE_admin','Kim','Kim','unknown','M','Y','Vietnam','Vietnam');


CREATE TABLE Service (
  serviceID       INT     AUTO_INCREMENT,
  provider      VARCHAR(30)  NOT NULL,
  name          VARCHAR(40)  NOT NULL,
  currentPrice  DOUBLE      NOT NULL,
  originalPrice DOUBLE      NOT NULL    DEFAULT 0,
  description   VARCHAR(200) NOT NULL,
  capacity      INT          NOT NULL,
  number_of_deal INT         NOT NULL   DEFAULT 0,
  RegisterTime   TIMESTAMP   NOT NULL   DEFAULT current_timestamp(),
  nationality    VARCHAR(20) NOT NULL,
  rank           DOUBLE      NOT NULL,
  type           VARCHAR(20) NOT NULL,
  image          VARCHAR(200),
  CONSTRAINT service_pk PRIMARY KEY (serviceID),
  CONSTRAINT service_fk1 FOREIGN KEY (provider) REFERENCES User (username)
);

insert into Service(serviceID, provider, name, currentPrice, description, capacity,nationality, rank, type, image)
            VALUES (0,'xz906@uowmail.edu.au','Database Teaching',40,'This is the tutorial about the oracle database.',30,'All',5.0,'tutorial','');
insert into Service(serviceID, provider, name, currentPrice, description, capacity,nationality, rank, type, image)
            VALUES (0,'jz491@uowmail.edu.au','C++ Teaching',30,'This is the tutorial about c++ programming language.',30,'All',5.0,'tutorial','');
insert into Service(serviceID, provider, name, currentPrice, description, capacity,nationality, rank, type, image)
            VALUES (0,'jr239@uowmail.edu.au','how to write good report',20,'This is the tutorial about the method of writing report.',30,'All',5.0,'tutorial','');
insert into Service(serviceID, provider, name, currentPrice, description, capacity,nationality, rank, type, image)
            VALUES (0,'adk829@uowmail.edu.au','AI information',35,'This are some news about AI.',50,'All',5.0,'book','');
insert into Service(serviceID, provider, name, currentPrice, description, capacity,nationality, rank, type, image)
            VALUES (0,'hkn193@uowmail.edu.au','Spring framework using',40,'This is the tutorial teaching how to use spring framework.',30,'All',5.0,'tutorial','');


CREATE TABLE SearchRecord (
  recordID   INT                 AUTO_INCREMENT,
  username VARCHAR(30) NOT NULL,
  searchInfo VARCHAR(100),
  searchTime TIMESTAMP NOT NULL  DEFAULT current_timestamp(),
  CONSTRAINT searchRecord_pk PRIMARY KEY (recordID),
  CONSTRAINT searchRecord_fk FOREIGN KEY (username) REFERENCES User (username)
);

insert into SearchRecord(recordID, username, searchInfo) VALUES (0,'xz906@uowmail.edu.au','database');
insert into SearchRecord(recordID, username, searchInfo) VALUES (0,'xz906@uowmail.edu.au','c++');
insert into SearchRecord(recordID, username, searchInfo) VALUES (0,'xz906@uowmail.edu.au','AI');
insert into SearchRecord(recordID, username, searchInfo) VALUES (0,'jr239@uowmail.edu.au','database');
insert into SearchRecord(recordID, username, searchInfo) VALUES (0,'adk829@uowmail.edu.au','spring framework');
insert into SearchRecord(recordID, username, searchInfo) VALUES (0,'hkn193@uowmail.edu.au','AI information');


CREATE TABLE Favourites (
  username  VARCHAR(30) NOT NULL,
  serviceID INT         NOT NULL,
  CONSTRAINT favourites_pk PRIMARY KEY (username, serviceID),
  CONSTRAINT favourites_fk1 FOREIGN KEY (username) REFERENCES user(username),
  CONSTRAINT favourites_fk2 FOREIGN KEY (serviceID) REFERENCES Service(serviceID)
);

insert into Favourites VALUES ('xz906@uowmail.edu.au',1);
insert into Favourites VALUES ('xz906@uowmail.edu.au',2);
insert into Favourites VALUES ('hkn193@uowmail.edu.au',3);
insert into Favourites VALUES ('hkn193@uowmail.edu.au',5);
insert into Favourites VALUES ('hkn193@uowmail.edu.au',4);


CREATE TABLE Purchased (
  PurchasedID   INT                   AUTO_INCREMENT,
  username    VARCHAR(30) NOT NULL,
  serviceID   INT         NOT NULL,
  purchasedTime TIMESTAMP NOT NULL    DEFAULT current_timestamp(),
  providerCheck CHAR      NOT NULL    DEFAULT 'N',
  customerCheck CHAR      NOT NULL    DEFAULT 'N',
  quantity      INT       NOT NULL,
  transferredfrom  int,
  CONSTRAINT purchased_pk PRIMARY KEY (PurchasedID),
  CONSTRAINT purchased_fk1 FOREIGN KEY (username) REFERENCES User (username),
  CONSTRAINT purchased_fk2 FOREIGN KEY (serviceID) REFERENCES Service (serviceID),
  CONSTRAINT purchased_check1 CHECK (providerCheck = 'Y' OR providerCheck = 'N'),
  CONSTRAINT purchased_check2 CHECK (customerCheck = 'Y' OR customerCheck = 'N')
);

insert into Purchased(username, serviceID, providerCheck, customerCheck, quantity)
              VALUES ('hkn193@uowmail.edu.au',3,'Y','Y',1);
insert into Purchased(username, serviceID, providerCheck, customerCheck, quantity, transferredfrom)
              VALUES ('hkn193@uowmail.edu.au',2,'Y','Y',1,1);
insert into Purchased(username, serviceID, providerCheck, customerCheck, quantity)
              VALUES ('hkn193@uowmail.edu.au',4,'Y','Y',1);
insert into Purchased(username, serviceID, providerCheck, customerCheck, quantity)
              VALUES ('hkn193@uowmail.edu.au',5,'Y','Y',1);


CREATE TABLE Comment (
  commentID INT                      AUTO_INCREMENT,
  serviceID       INT          NOT NULL,
  commenter VARCHAR(30) NOT NULL,
  commentInfo VARCHAR(100) NOT NULL,
  visible     CHAR         NOT NULL  DEFAULT 'Y',
  commentTime TIMESTAMP    NOT NULL  DEFAULT current_timestamp(),
  previousComment INT,
  CONSTRAINT comment_pk PRIMARY KEY (commentID),
  CONSTRAINT comment_fk1 FOREIGN KEY (serviceID) REFERENCES Service (serviceID),
  CONSTRAINT comment_fk2 FOREIGN KEY (commenter) REFERENCES User (username),
  CONSTRAINT comment_check CHECK (visible = 'Y' OR visible = 'N')
);

insert into Comment(serviceID, commenter, commentInfo, visible)
            VALUES (1,'hkn193@uowmail.edu.au','This is a good tutorial. I will recommend it to my friends.','Y');
insert into Comment(serviceID, commenter, commentInfo, visible,previousComment)
            VALUES (1,'xz906@uowmail.edu.au','Thanks.','Y',1);
insert into Comment(serviceID, commenter, commentInfo, visible)
            VALUES (2,'hkn193@uowmail.edu.au','This is a good tutorial. I will recommend it to my friends.','Y');
insert into Comment(serviceID, commenter, commentInfo, visible)
            VALUES (3,'hkn193@uowmail.edu.au','This is a good tutorial. I will recommend it to my friends.','Y');
insert into Comment(serviceID, commenter, commentInfo, visible)
            VALUES (4,'hkn193@uowmail.edu.au','This is a good tutorial. I will recommend it to my friends.', 'Y');


CREATE TABLE Address (
  owner       VARCHAR(30) NOT NULL,
  type  VARCHAR(20) NOT NULL,
  country VARCHAR(30) NOT NULL,
  state   VARCHAR(30) NOT NULL,
  ZIPCode INT         NOT NULL,
  city    VARCHAR(30) NOT NULL,
  street  VARCHAR(30) NOT NULL,
  houseNumber INT         NOT NULL,
  unitNumber  INT         NOT NULL,
  CONSTRAINT address_pk PRIMARY KEY (owner, type),
  CONSTRAINT address_fk FOREIGN KEY (owner) REFERENCES User (username)
);

insert into Address VALUES ('xz906@uowmail.edu.au','home','Australia','NSW','2500','Wollongong','New Dapto Road',59,5);
insert into Address VALUES ('xz906@uowmail.edu.au','delivery','Australia','NSW','2500','Wollongong','New Dapto Road',59,5);
insert into Address VALUES ('hkn193@uowmail.edu.au','home','Australia','NSW','2500','Wollongong','New Dapto Road',57,1);
insert into Address VALUES ('hkn193@uowmail.edu.au','delivery','Australia','NSW','2500','Wollongong','New Dapto Road',57,1);


CREATE TABLE Discount (
  serviceID     INT         NOT NULL,
  discountValue DOUBLE NOT NULL,
  target        VARCHAR(30) NOT NULL,
  expiryDate    TIMESTAMP   NOT NULL,
  CONSTRAINT discount_pk PRIMARY KEY (serviceID, target),
  CONSTRAINT discount_fk1 FOREIGN KEY (serviceID) REFERENCES Service (serviceID),
  CONSTRAINT discount_fk2 FOREIGN KEY (target) REFERENCES User (username),
  CONSTRAINT discount_check CHECK (discountValue > 0 AND discountValue < 1)
);

insert into Discount VALUES (1,0.8,'hkn193@uowmail.edu.au',20171210);
insert into Discount VALUES (3,0.9,'hkn193@uowmail.edu.au',20171215);
insert into Discount VALUES (4,0.7,'hkn193@uowmail.edu.au',20171221);
insert into Discount VALUES (5,0.6,'hkn193@uowmail.edu.au',20171211);
insert into Discount VALUES (2,0.8,'hkn193@uowmail.edu.au',20171210);


CREATE TABLE CheckBoxMessage (
  MessageID   INT                    AUTO_INCREMENT,
  sender    VARCHAR(30)  NOT NULL,
  receiver  VARCHAR(30) NOT NULL,
  content   VARCHAR(300) NOT NULL,
  sendTime  TIMESTAMP    NOT NULL  DEFAULT current_timestamp(),
  readMessage CHAR       NOT NULL    DEFAULT 'N',
  CONSTRAINT message_pk PRIMARY KEY (MessageID),
  CONSTRAINT message_fk1 FOREIGN KEY (sender) REFERENCES User (username),
  CONSTRAINT message_fk2 FOREIGN KEY (receiver) REFERENCES User (username),
  CONSTRAINT message_check CHECK ( readMessage = 'Y' OR readMessage = 'N')
);

insert into CheckBoxMessage (sender, receiver, content,readMessage)
    VALUES ('hkn193@uowmail.edu.au','xz906@uowmail.edu.au','I wanna know about some information of database teaching. Could you tell me?','Y');
insert into CheckBoxMessage (sender, receiver, content,readMessage)
    VALUES ('xz906@uowmail.edu.au','hkn193@uowmail.edu.au','Of course. This tutorial will show four parts: insert, delete,update,select. And teaching some new methods.','Y');
insert into CheckBoxMessage (sender, receiver, content,readMessage)
    VALUES ('hkn193@uowmail.edu.au','xz906@uowmail.edu.au','Ok, I got it. Thanks a lot.','Y');


CREATE TABLE ShoppingCart (
  username  VARCHAR(30) NOT NULL,
  serviceID INT         NOT NULL,
  quantity  INT         NOT NULL    DEFAULT 1,
  CONSTRAINT shopping_pk PRIMARY KEY (username, serviceID),
  CONSTRAINT shopping_fk1 FOREIGN KEY (username) REFERENCES User (username),
  CONSTRAINT shopping_fk2 FOREIGN KEY (serviceID) REFERENCES Service (serviceID)
);

insert into ShoppingCart VALUES ('hkn193@uowmail.edu.au',1,1);
insert into ShoppingCart VALUES ('hkn193@uowmail.edu.au',2,1);
insert into ShoppingCart VALUES ('hkn193@uowmail.edu.au',3,1);
insert into ShoppingCart VALUES ('hkn193@uowmail.edu.au',4,1);

